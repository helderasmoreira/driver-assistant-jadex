package movement;

import jadex.bdi.planlib.PlanFinishedTaskCondition;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector1Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.PathFinder;
import algorithms.PathFinder.Node;
import application.Utils;
import java.util.Iterator;
import java.util.Set;

/**
 *  The move to a location plan.
 */
public class MoveToLocationPlan extends Plan
{
	/**
	 *  The plan body.
	 */
	public void body()
	{
//		System.out.println("MoveToLocation: "+getComponentIdentifier());
		
		ISpaceObject myself	= (ISpaceObject)getBeliefbase().getBelief("myself").getFact();
		IVector2 dest = (IVector2)getParameter("destination").getValue();
		getBeliefbase().getBelief("poi").setFact(getParameter("destination").getValue());
		
		Map props = new HashMap();
		props.put(MoveTask.PROPERTY_DESTINATION, dest);
		props.put(MoveTask.PROPERTY_SCOPE, getScope().getExternalAccess());
		props.put(AbstractTask.PROPERTY_CONDITION, new PlanFinishedTaskCondition(getPlanElement()));
		IEnvironmentSpace space = (IEnvironmentSpace)getBeliefbase().getBelief("environment").getFact();
		
		int sizex = ((Space2D)space).getAreaSize().getXAsInteger();
		int sizey = ((Space2D)space).getAreaSize().getYAsInteger();
		
		
		PathFinder pf = new PathFinder(Utils.map);
		
		List<Node> nodes = pf.compute(new PathFinder.Node(((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
				((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()), 
				new PathFinder.Node(dest.getXAsInteger(), 
						dest.getYAsInteger()));
		
		/*System.out.println(((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger() +  " " + 
				((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger());
		System.out.println(dest.getXAsInteger()+ " "+ 
						dest.getYAsInteger());*/
		
		if(nodes == null)
		{
			//System.out.println("No path");
			return;
		}
		else{
			//System.out.println(nodes.toString());
			nodes.remove(0);
			props.put("path", nodes);
		}

               boolean fim = true;
               final Set objects = ((Space2D)space).getNearObjects(new Vector2Int(nodes.get(nodes.size()-1).x, nodes.get(nodes.size()-1).y), new Vector1Double(1.0));
               for (Iterator it = objects.iterator(); it.hasNext();) {
                final ISpaceObject so = (ISpaceObject) it.next();
                if (so.getType().equals("pointofinterest")) {
                    Utils.dialog.changeText("Vou para: " + so.getProperty("type") + ".");
                    fim = false;
                    break;
                    }
                }

                if(fim) Utils.dialog.changeText("Vou para o ponto final.");
             
		Object taskid = space.createObjectTask(MoveTask.PROPERTY_TYPENAME, props, myself.getId());
//		move	= new MoveTask(dest, res, getExternalAccess());
//		myself.addTask(move);
		SyncResultListener	res	= new SyncResultListener();
		space.addTaskListener(taskid, myself.getId(), res);
		res.waitForResult();
	}
}
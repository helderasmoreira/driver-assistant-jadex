package driver;

import algorithms.PathFinder;
import algorithms.PathFinder.Node;
import application.Utils;
import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.IPlan;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;

public class AvoidAccident  extends Plan {

		@Override
		public void body() {
			ISpaceObject acidente = (ISpaceObject)getParameter("target").getValue();
			IVector2 vpoi = (IVector2) getParameter("poi").getValue(); 
			IVector2 vacidente = (IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION);
			
			acidente.setProperty("state", "avoid");
			
			if(acidente.getProperty("weather").equals((String)getBeliefbase().getBelief("weather").getFact()) || acidente.getProperty("weather").equals("any")) {
				PathFinder pf = new PathFinder(Utils.map);
				pf.markAccident(new Node(vacidente.getXAsInteger(), vacidente.getYAsInteger()));
			}
			
			IGoal go_target = createGoal("goDestiny");
		//	go_target.getParameter("poi").setValue(vpoi);
		//	go_target.getParameter("acidente").setValue(vacidente);
		//	go_target.getParameter("avoidAccident").setValue(true);
		
			dispatchTopLevelGoal(go_target);
}
		}

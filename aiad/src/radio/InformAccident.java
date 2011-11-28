package radio;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.extension.agr.AGRSpace;
import jadex.extension.agr.Group;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;

public class InformAccident extends Plan {

	@Override
	public void body() {
		
		IMessageEvent me = createMessageEvent("inform_accident");
		
		AGRSpace agrs = (AGRSpace)((IExternalAccess)getScope().getParent()).getExtension("mundo").get(this);
		
		Group group = agrs.getGroup("agents");
		IComponentIdentifier[]	drivers	= group.getAgentsForRole("Driver");
		
		Space2D	space	= (Space2D)getBeliefbase().getBelief("environment").getFact();
		ISpaceObject[]	accidents	= space.getSpaceObjectsByType("accident");
		System.out.println(accidents);
		if(accidents.length != 0)
		{
			me.getParameter(SFipa.RECEIVERS).setValue(drivers);
			me.getParameter(SFipa.CONTENT).setValue(accidents); 
			sendMessage(me);
			
		}
		
		int acc = accidents.length;
		
		while(true)
		{
			if(space.getSpaceObjectsByType("accident").length != acc)
			{
				acc = space.getSpaceObjectsByType("accident").length;
				accidents	= space.getSpaceObjectsByType("accident");
				me = createMessageEvent("inform_accident");
				me.getParameter(SFipa.RECEIVERS).setValue(drivers);
				me.getParameter(SFipa.CONTENT).setValue(accidents); 
				sendMessage(me);
			}
		}
		
	}
}

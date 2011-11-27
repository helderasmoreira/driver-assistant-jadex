package driver;

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
		
		System.out.println("init");
		IMessageEvent me = createMessageEvent("inform_accident");
		
		AGRSpace agrs = (AGRSpace)((IExternalAccess)getScope().getParent()).getExtension("mundo").get(this);
		
		Group group = agrs.getGroup("agents");
		IComponentIdentifier[]	drivers	= group.getAgentsForRole("Driver");
		
		Space2D	space	= (Space2D)getBeliefbase().getBelief("environment").getFact();
		ISpaceObject[]	accidents	= space.getSpaceObjectsByType("accident");
		if(accidents.length != 0)
		{
			me.getParameterSet(SFipa.RECEIVERS).addValue(drivers);
			me.getParameter(SFipa.CONTENT).setValue(accidents); 
			sendMessage(me);
			System.out.println("Enviei " + drivers.length);
		}
	}
}

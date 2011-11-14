package driver;

import application.Utils;
import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;

public class AccidentInform extends Plan{

	@Override
	public void body() {
		
		System.out.println("Recebi");
		IMessageEvent req = (IMessageEvent)getReason();
		
		ISpaceObject[] ot = (ISpaceObject[])req.getParameter(SFipa.CONTENT).getValue();
		
		for(ISpaceObject acc : ot)
		{
			Utils.map[((IVector2) acc.getProperty("position")).getYAsInteger()] 
			          [((IVector2) acc.getProperty("position")).getXAsInteger()] = 0;
		}
		
	}

}

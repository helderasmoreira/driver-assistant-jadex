package radio;

import application.Utils;
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


        AGRSpace agrs = (AGRSpace) ((IExternalAccess) getScope().getParent()).getExtension("mundo").get(this);

        Group group = agrs.getGroup("agents");
        IComponentIdentifier[] drivers = group.getAgentsForRole("Driver");
        IComponentIdentifier[] drivers2 = group.getAgentsForRole("Driver2");

        Space2D space = (Space2D) getBeliefbase().getBelief("environment").getFact();
        ISpaceObject[] accidents = space.getSpaceObjectsByType("accident");
        IMessageEvent me = createMessageEvent("inform_accident");
        if (accidents.length != 0 && Utils.radio) {
            me.getParameter(SFipa.RECEIVERS).setValue(drivers);
            me.getParameter(SFipa.CONTENT).setValue(accidents);
            sendMessage(me);


            me = createMessageEvent("inform_accident");
            me.getParameter(SFipa.RECEIVERS).setValue(drivers2);
            me.getParameter(SFipa.CONTENT).setValue(accidents);
            sendMessage(me);
            
        }

        int acc = accidents.length;

        while (true) {
            System.out.flush();
            if (Utils.radio) {
                if (space.getSpaceObjectsByType("accident").length != acc || Utils.firstRadio) {
                    Utils.firstRadio = false;
                    acc = space.getSpaceObjectsByType("accident").length;
                    accidents = space.getSpaceObjectsByType("accident");
                    me = createMessageEvent("inform_accident");
                    me.getParameter(SFipa.RECEIVERS).setValue(drivers);
                    me.getParameter(SFipa.CONTENT).setValue(accidents);
                    sendMessage(me);

                    me = createMessageEvent("inform_accident");
                    me.getParameter(SFipa.RECEIVERS).setValue(drivers2);
                    me.getParameter(SFipa.CONTENT).setValue(accidents);
                    sendMessage(me);
                }
            } else if(Utils.acidente != null && drivers2 != null)
            {
                ISpaceObject[] accs = new ISpaceObject[1];
                accs[0] = Utils.acidente;
                me = createMessageEvent("inform_accident");
                me.getParameter(SFipa.RECEIVERS).setValue(drivers);
                me.getParameter(SFipa.CONTENT).setValue(accs);
                sendMessage(me);

                me = createMessageEvent("inform_accident");
                me.getParameter(SFipa.RECEIVERS).setValue(drivers2);
                me.getParameter(SFipa.CONTENT).setValue(accs);
                sendMessage(me);

                Utils.acidente = null;
            }
        }

    }
}

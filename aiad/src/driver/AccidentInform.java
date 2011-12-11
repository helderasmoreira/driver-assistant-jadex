package driver;


import application.Utils;
import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;

public class AccidentInform extends Plan {

    @Override
    public void body() {

        IMessageEvent req = (IMessageEvent) getReason();

        ISpaceObject[] ot = (ISpaceObject[]) req.getParameter(SFipa.CONTENT).getValue();
        Utils.dialog.jTable1.setValueAt("", 4, 1);
        Utils.dialog.jTable1.setValueAt("", 1, 1);
       
        for (ISpaceObject acc : ot) {
           
                if (Utils.dialog.jTable1.getValueAt(1, 1) != "") {
                    Utils.dialog.jTable1.setValueAt(Utils.dialog.jTable1.getValueAt(1, 1) + " | " + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getY(), 1, 1);
                } else {
                    Utils.dialog.jTable1.setValueAt(((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getY(), 1, 1);
               }
              if (Utils.map[((IVector2) acc.getProperty("position")).getYAsInteger()][((IVector2) acc.getProperty("position")).getXAsInteger()] != 0) {
                  
                if (Utils.dialog.jTable1.getValueAt(4, 1) != "") {
                    Utils.dialog.jTable1.setValueAt(Utils.dialog.jTable1.getValueAt(4, 1) + " | " + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getY(), 4, 1);
                } else {
                    Utils.dialog.jTable1.setValueAt(((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acc.getProperty(Space2D.PROPERTY_POSITION)).getY(), 4, 1);
                    }
            }

            Utils.map[((IVector2) acc.getProperty("position")).getYAsInteger()][((IVector2) acc.getProperty("position")).getXAsInteger()] = 0;

        }

        if(Utils.acc != null)
        {
             Utils.dialog.jTable1.setValueAt("Saiu acidente: " + ((IVector2) Utils.acc.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) Utils.acc.getProperty(Space2D.PROPERTY_POSITION)).getY(), 4, 1);
             Utils.acc = null;
        }



        getBeliefbase().getBelief("newradiomsg").setFact(true);

    }
}

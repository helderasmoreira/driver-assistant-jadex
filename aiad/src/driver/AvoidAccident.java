package driver;

import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import algorithms.PathFinder;
import algorithms.PathFinder.Node;
import application.Utils;

public class AvoidAccident extends Plan {

    @Override
    public void body() {
        ISpaceObject acidente = (ISpaceObject) getParameter("target").getValue();
        IVector2 vacidente = (IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION);
        Space2D space = (Space2D) getBeliefbase().getBelief("environment").getFact();
        acidente.setProperty("state", "avoid");

        if (acidente.getProperty("weather").equals((String) space.getProperty("weather")) || acidente.getProperty("weather").equals("any")) {
            
            if (Utils.map[vacidente.getYAsInteger()][vacidente.getXAsInteger()] != 0) {
              
                if (Utils.dialog.jTable1.getValueAt(1, 1) != null) {
                    Utils.dialog.jTable1.setValueAt(Utils.dialog.jTable1.getValueAt(1, 1) + " | " + ((IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION)).getY(), 1, 1);
                   
                } else {
                    Utils.dialog.jTable1.setValueAt(((IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION)).getX() + "," + ((IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION)).getY(), 1, 1);
                   
                }
            }
            Utils.markAccident(new Node(vacidente.getXAsInteger(), vacidente.getYAsInteger()));
        }


        Utils.dialog.changeText("Encontrei um acidente!");
        Utils.dialog.changeText("Recalculando caminho...");

        IGoal go_target = createGoal("goDestiny");

        dispatchTopLevelGoal(go_target);
    }
}

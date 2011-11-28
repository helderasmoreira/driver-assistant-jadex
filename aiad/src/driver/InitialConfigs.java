/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import application.Utils;
import jadex.bdi.runtime.Plan;
import visual.WorldOptions;

/**
 *
 * @author Tiago
 */
public class InitialConfigs extends Plan {

    @Override
    public void body() {
        
        
        Utils.worldOptionsDialog = new WorldOptions(getExternalAccess());
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
               
                Utils.worldOptionsDialog.setVisible(true);

            }
        });
        
        
        
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JDialog;
import lp.LPApp;
import lp.eresource.RadarPanel;

/**
 *
 * @author t
 */
public final class PopCenterDialog extends JDialog {

    public PopCenterDialog(javax.swing.JPanel panel) {
        super(LPApp.getApplication().getMainFrame(), true);
        setContentPane(panel);
        //this.setVisible(true);
        //centerScreen();
    }

    public PopCenterDialog()
    {
        this.setVisible(true);
    }

    public PopCenterDialog(String title, RadarPanel radar)
    {
        super(LPApp.getApplication().getMainFrame(), title, true);
        this.setContentPane(radar);
        radar.setDParent(this);
        pack();
        centerScreen();
        this.setVisible(true);
    }

    public void centerScreen() {
        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
                (dim.height - abounds.height) / 2);
        requestFocus();
    }
}

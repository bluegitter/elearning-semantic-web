/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JDialog;

/**
 *
 * @author t
 */
public class PopCenterDialog extends JDialog {

    public PopCenterDialog() {
        this.setVisible(true);
        centerScreen();
    }

    public void centerScreen() {
        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
                (dim.height - abounds.height) / 2);
        requestFocus();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JDialog;
import javax.swing.JLabel;
import lp.LPView;

/**
 *
 * @author student
 */
public class PopMesDialog extends JDialog {

    public PopMesDialog() {
        centerScreen();
        this.setVisible(true);
    }
    public void setMessage(String message){
        JLabel content = new JLabel(message);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
    }
    private void centerScreen() {
        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
                (dim.height - abounds.height) / 2);
        requestFocus();
    }
    
}

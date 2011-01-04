/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp;

import javax.swing.JDialog;

/**
 *
 * @author ghh
 */
public class NavigatorDialog extends JDialog{
    public javax.swing.JPanel[] panels = {new RegistPane(this), new NavigatorPane(this)};
    private int panel_index = 0;
    public NavigatorDialog(javax.swing.JFrame f) {
        super(f);
        this.setContentPane(panels[panel_index]);
    }

    public void setNext() {
        this.panel_index++;
        this.setContentPane(panels[panel_index]);
        this.pack();
    }

    public void setPrevious() {
        this.panel_index--;
        this.setContentPane(panels[panel_index]);
        this.pack();
    }

}

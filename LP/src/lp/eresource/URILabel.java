/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author william
 */
public class URILabel extends JLabel implements java.awt.event.MouseListener {

    public URILabel() {
        super();
    }

    public URILabel(String string) {
        super(string);
        this.setForeground(new Color(0,0,255));
    }
    public void updateURILabel(){
        System.out.println("update uri label");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("click label");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

    @Override
    public void mouseExited(MouseEvent e) {
          this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    public String toString(){
        return this.getText();
    }
}

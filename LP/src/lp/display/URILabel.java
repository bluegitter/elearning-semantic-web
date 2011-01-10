/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.display;

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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

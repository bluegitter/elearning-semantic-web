/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jena.impl.ELearnerModelImpl;

/**
 *
 * @author student
 */
public class IdCheckListener implements ListSelectionListener {

    public static int ADD_CONCEPT_ID = 1;
    public static int ADD_RESOURCE_ID = 2;
    public static int ADD_GOAL_ID = 3;
    private ELearnerModelImpl emi;
    private int type;

    public IdCheckListener(int type, ELearnerModelImpl emi) {
        this.type = type;
        this.emi = emi;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        switch (type) {
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
                break;
            default:
                System.out.println("default");
        }
    }
}

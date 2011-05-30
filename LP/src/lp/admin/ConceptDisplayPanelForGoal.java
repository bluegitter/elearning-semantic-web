/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.util.HashSet;
import javax.swing.JTable;
import jena.impl.ELearnerModelImpl;
import jena.impl.OwlUpdate;
import lp.LPApp;
import ontology.EConcept;
import ontology.EGoal;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class ConceptDisplayPanelForGoal extends ConceptSelectPanel {

    public ConceptDisplayPanelForGoal() {
        super();
        jButton3.setText("为目标删除知识点");
    }

    public ConceptDisplayPanelForGoal(ELearnerModelImpl emi, HashSet<EConcept> cons) {
        super(emi, cons);
        jButton3.setText("为目标删除知识点");
    }

    @Override
    public void buttonAction() {
        System.out.println("remove concepts for goals");
        JTable table = getTable();
        int[] is = table.getSelectedRows();
        for (int i : is) {
            String cid = table.getModel().getValueAt(i, 0).toString();
            emi.removePropertyContainsConcept(goal, emi.getEConcept(cid));
            adminPanel.getPropertyContainsConcept(OwlUpdate.optypes[OwlUpdate.REMOVEOBJECTPROPERTY], goal.getGid(), cid);
        }
        this.adminPanel.updateEditGoalPanel();
    }

    public void setGoal(EGoal goal) {
        this.goal = goal;
    }

    public EGoal getGoal() {
        return goal;
    }
    public EGoal goal;
}

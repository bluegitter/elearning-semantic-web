/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.util.HashSet;
import javax.swing.JTable;
import jena.impl.ELearnerModelImpl;
import jena.impl.OwlUpdate;
import ontology.EConcept;
import ontology.EGoal;

/**
 *
 * @author student
 */
public class ConceptSelectPanelForGoal extends ConceptSelectPanel {

    public ConceptSelectPanelForGoal() {
        super();
        jButton3.setText("为目标添加知识点");
    }

    public ConceptSelectPanelForGoal(ELearnerModelImpl emi, HashSet<EConcept> cons) {
        super(emi, cons);
        jButton3.setText("为目标添加知识点");
    }

    @Override
    public void buttonAction() {
        System.out.println("add concepts for goals");
        JTable table = getTable();
        int[] is = table.getSelectedRows();
        for (int i : is) {
            String cid = table.getModel().getValueAt(i, 0).toString();
            emi.addPropertyContainsConcept(goal, emi.getEConcept(cid));
            adminPanel.getPropertyContainsConcept(OwlUpdate.optypes[OwlUpdate.ADDOBJECTPROPERTY],goal.getGid(),cid);
        }
        this.adminPanel.updateEditGoalPanel();
    }

    public void setGoal(EGoal goal) {
        this.goal = goal;
    }

    public EGoal getGoal() {
        return goal;
    }
    private EGoal goal;
}

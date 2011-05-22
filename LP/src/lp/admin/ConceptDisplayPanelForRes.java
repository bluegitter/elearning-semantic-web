/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.util.HashSet;
import javax.swing.JTable;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class ConceptDisplayPanelForRes extends ConceptSelectPanel {

    public ConceptDisplayPanelForRes() {
        super();
        jButton3.setText("为资源添加知识点");
    }

    public ConceptDisplayPanelForRes(ELearnerModelImpl emi, HashSet<EConcept> cons) {
        super(emi, cons);
        this.cons = cons;
        jButton3.setText("为资源添加知识点");
    }

    @Override
    public void buttonAction() {
        System.out.println("remove Concept for res");
        JTable table = getTable();
        int[] is = table.getSelectedRows();
        for (int i : is) {
            String cid = table.getModel().getValueAt(i, 0).toString();
            emi.removePropertyIsResourceOfC(res, emi.getEConcept(cid));
        }
        this.adminPanel.updateEditResPanel();
    }
    private ISCB_Resource res;

    public ISCB_Resource getRes() {
        return res;
    }

    public void setRes(ISCB_Resource res) {
        this.res = res;
    }
}

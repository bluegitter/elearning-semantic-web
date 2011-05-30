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
import ontology.resources.ISCB_Resource;

/**
 *
 * @author student
 */
public class ConceptSelectPanelForRes extends ConceptSelectPanel {

    public ConceptSelectPanelForRes() {
        super();
        jButton3.setText("为资源添加知识点");
    }

    public ConceptSelectPanelForRes(ELearnerModelImpl emi,HashSet<EConcept> cons) {
        super(emi,cons);
        this.cons = cons;
        jButton3.setText("为资源添加知识点");
    }

    @Override
    public void buttonAction() {
        System.out.println("add Concept for res");
        System.out.println("remove Concept for res");
        JTable table = getTable();
        int[] is = table.getSelectedRows();
        for (int i : is) {
            String cid = table.getModel().getValueAt(i, 0).toString();
            emi.addPropertyIsResourceOfC(res, emi.getEConcept(cid));
             adminPanel.getPropertyIsResourceOfC(OwlUpdate.optypes[OwlUpdate.ADDOBJECTPROPERTY],res.getRid(),cid);
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

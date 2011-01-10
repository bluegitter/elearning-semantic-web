/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jena.impl.ELearnerModelImpl;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author william
 */
public class ResourceTable extends JTable implements MouseListener {

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl();
        ArrayList<ISCB_Resource> res = emi.getEResourcesByEConcept(emi.getEConcept("cid1"));
        System.out.println("res size" + res.size());
        JFrame f = new JFrame();
        ResourceTable rt = new ResourceTable(res);
        f.add(rt);
        f.pack();
        f.setVisible(true);
    }

    public ResourceTable() {
        super();
    }

    public ResourceTable(ArrayList<ISCB_Resource> res) {
        super();
        this.model = (DefaultTableModel) super.getModel();
        this.res = res;
        for (ISCB_Resource er : res) {
            Object[] oa = {er.getName(), er.getDifficulty(), new javax.swing.JLabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
            model.addRow(oa);
        }
    }

    public void updateRes(ArrayList<ISCB_Resource> ra) {
        for (ISCB_Resource er : ra) {
//            Object[] oa = {er.getName(), er.getDifficulty(), new javax.swing.JLabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
            Object[] oa = {er.getName(), er.getDifficulty(), new URILabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
            model.addRow(oa);
        }
    }

    public void clearModel() {
        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }
    }
    private DefaultTableModel model;
    private ArrayList<ISCB_Resource> res;

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

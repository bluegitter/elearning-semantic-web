/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.display;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        myInit();
    }

    public ResourceTable(ArrayList<ISCB_Resource> res) {
        super();
        this.res = res;
        myInit();
        this.updateRes(res);
    }

    public void myInit() {
        addMouseListener(this);
        this.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "资源名", "难易度", "下载"
                }) {

            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        this.setName("resourceTable");
    }

    public void updateRes(ArrayList<ISCB_Resource> ra) {
        DefaultTableModel model = (DefaultTableModel) super.getModel();
        for (ISCB_Resource er : ra) {
            Object[] oa = {er.getName(), er.getDifficulty(), new javax.swing.JLabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
//            Object[] oa = {er.getName(), er.getDifficulty(), new URILabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
            model.addRow(oa);
        }
    }

    public void clearModel() {
        DefaultTableModel model = (DefaultTableModel) super.getModel();
        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }
    }
    private ArrayList<ISCB_Resource> res;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().isEnabled() &&e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
            Point p = e.getPoint();
            int row = this.rowAtPoint(p);
            int column = this.columnAtPoint(p);
            if(column ==2){
                JLabel label = (JLabel)this.getModel().getValueAt(row, column);
                label.setBackground(Color.BLUE);
                System.out.println("uri label:"+label.getText());
            }
        }
  }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("enter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("exited");
    }
}

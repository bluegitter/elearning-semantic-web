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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import jena.impl.ELearnerModelImpl;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author william
 */
public class ResourceTable extends JTable implements MouseListener {

    private ArrayList<ISCB_Resource> res;
    public int pages;
    public int currentPage;
    private DefaultTableModel model;

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
        this.res = new ArrayList<ISCB_Resource>();
        myInit();
    }

    public ResourceTable(ArrayList<ISCB_Resource> res) {
        super();
        this.res = res;
        myInit();
//        this.updateRes(res);
        this.updatePage();
    }

    private void myInit() {
        addMouseListener(this);
        initPages();
        this.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "资源名", "资源链接", "难易度"
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
        model = (DefaultTableModel) super.getModel();
        TableColumn uriColumn = this.getColumn("资源链接");
        DefaultTableCellRenderer fontColor = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                setForeground(new Color(0,0,255));
                if(value !=null){
                    setText(value.toString());
                }else{
                    setText("");
                }
            }
        };
        uriColumn.setCellRenderer(fontColor);
    }

    private void initPages() {
        if (res.size() > 10) {
            int size = res.size();
            if (size / 10 * 10 < size) {
                pages = size / 10 + 1;
            } else {
                pages = res.size() / 10;
            }
            currentPage = 0;
        } else {
            pages = 1;
            currentPage = 0;
        }
    }

    private void updatePage() {
        clearModel();
        if (currentPage != pages - 1) {
            int start = currentPage * 10 + 0;
            for (int i = start; i < start + 10; i++) {
                ISCB_Resource er = res.get(i);
                addEResourceToTableModel(er);
            }
        } else if (currentPage == pages - 1) {
            int start = currentPage * 10 + 0;
            int end = res.size();
            for (int i = start; i < end; i++) {
                ISCB_Resource er = res.get(i);
                addEResourceToTableModel(er);
            }
        } else {
            System.out.println("pages are out of the range");
        }
    }

    public void nextPage() {
        if (currentPage < (pages - 1)) {
            currentPage++;
        } else {
            System.out.println("next page opertaion error. current page out of range. CurrentPage: " + currentPage);
        }
        updatePage();
    }

    public void lastPage() {
        if (currentPage > 0) {
            currentPage--;
        } else {
            System.out.println("last page opertaion error. current page out of range. CurrentPage: " + currentPage);
        }
        updatePage();
    }

    private void addEResourceToTableModel(ISCB_Resource er) {
        Object[] oa = {er.getName(), new URILabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid()), er.getDifficulty()};
        model.addRow(oa);
    }

    public void updateRes(ArrayList<ISCB_Resource> ra) {
//        for (ISCB_Resource er : ra) {
//            addEResourceToTableModel(er);
//        }
        res = ra;
        initPages();
        updatePage();
    }

    public void clearModel() {
        DefaultTableModel model = (DefaultTableModel) super.getModel();
        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            Point p = e.getPoint();
            int row = this.rowAtPoint(p);
            int column = this.columnAtPoint(p);
            if (column == 2) {
                JLabel label = (JLabel) this.getModel().getValueAt(row, column);
                label.setBackground(Color.BLUE);
                System.out.println("uri label:" + label.getText());
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

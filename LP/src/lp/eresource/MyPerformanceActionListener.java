/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import lp.log.PopCenterDialog;
import ontology.EPerformance;

/**
 *
 * @author t
 */
public class MyPerformanceActionListener implements MouseListener {

    private JTable table;
    private MyPerformancePane performPane;

    public MyPerformanceActionListener(MyPerformancePane performPane) {
        this.performPane = performPane;
        this.table = performPane.concepts;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getComponent().isEnabled()) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            int column = table.columnAtPoint(p);
            System.out.println("row" + row + "\tcolumn" + column);
            if (column == 2) {
                EPerformance perform = performPane.perList.get(row);
                RadarPanel radar = new RadarPanel(perform);
                PopCenterDialog pcd = new PopCenterDialog("知识点自我评估", radar);
            }
        }
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

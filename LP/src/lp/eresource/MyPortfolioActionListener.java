/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lp.LPApp;
import lp.log.PopCenterDialog;
import lp.log.PopMesDialog;
import ontology.EConcept;
import ontology.EPortfolio;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author student
 */
public class MyPortfolioActionListener implements MouseListener {

    private JTable table;
    private MyPortfolioPane portfolioPane;

    public MyPortfolioActionListener(MyPortfolioPane portfolioPane) {
        this.portfolioPane = portfolioPane;
        this.table = portfolioPane.resources;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getComponent().isEnabled()) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            int column = table.columnAtPoint(p);
            if (column == 2) {
                EPortfolio portfolio = portfolioPane.resourceList.get(row);
                ISCB_Resource res = portfolio.getEResource();
                if (portfolio.getRate() == 0) {
                    PopCenterDialog pcd = new PopCenterDialog();
                    pcd.setContentPane(new ResourceRatingPane(portfolio, portfolioPane, pcd));
                    pcd.setTitle("资源评价");
                    pcd.pack();
                    pcd.centerScreen();
                } else {
                    PopCenterDialog pcd = new PopCenterDialog();
                    pcd.setContentPane(new ResourceRatingPane(portfolio, portfolioPane, pcd));
                    pcd.setTitle("资源评价内容");
                    pcd.pack();
                    pcd.centerScreen();
                }
            }
            if (column == 3) {
                PopCenterDialog conDisplay = new PopCenterDialog();
                EPortfolio f = portfolioPane.resourceList.get(row);
                ISCB_Resource res = f.getEResource();
                ArrayList<EConcept> con = LPApp.lpModel.getEConcepts(res);
                StringBuilder sb = new StringBuilder();
                for (EConcept c : con) {
                    sb.append(c.getName());
                    sb.append("\n");
                }
                JTextArea text = new JTextArea(sb.toString());
                text.setVisible(true);
                conDisplay.setContentPane(text);
                conDisplay.setTitle("与资源关联的知识点");
                conDisplay.pack();
                conDisplay.centerScreen();

            }
//            System.out.println("row" + row + "\tcolumn" + column);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import lp.LPApp;
import lp.MyPortfolioPane;
import lp.log.PopMesDialog;
import ontology.EConcept;
import ontology.EPortfolio;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author student
 */
public class MyPortfolioActionListener implements MouseListener {

    private ArrayList<EPortfolio> resourceList;
    private JTable table;
    private MyPortfolioPane portfolioPane;
    public MyPortfolioActionListener(MyPortfolioPane portfolioPane, ArrayList<EPortfolio> resourceList) {
        this.portfolioPane = portfolioPane;
        this.table = portfolioPane.resources;
        this.resourceList = resourceList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            int column = table.columnAtPoint(p);
            if (column == 2) {
                EPortfolio f = resourceList.get(row);
                ISCB_Resource res = f.getEResource();
                if (f.getRate() == 0) {
                    ResourceRatingDialog resRate = new ResourceRatingDialog(f);
                    resRate.setTitle("资源评价");
                    resRate.pack();
                } else {
                    ResourceRatingDialog resRate = new ResourceRatingDialog(f);
                    resRate.setTitle("资源评价内容");
                    resRate.pack();
                }

            }
            if (column == 3) {
                PopMesDialog conDisplay = new PopMesDialog();
                EPortfolio f = resourceList.get(row);
                ISCB_Resource res = f.getEResource();
                ArrayList<EConcept> con = LPApp.lpModel.getEConcepts(res);
                StringBuilder sb = new StringBuilder();
                for (EConcept c : con) {
                    sb.append(c.getName());
                    sb.append(" | ");
                }
                sb.deleteCharAt(sb.length() - 1);
                JTextField text = new JTextField(sb.toString());
                text.setVisible(true);
                conDisplay.setContentPane(text);
                conDisplay.setTitle("与资源关联的知识点");
                conDisplay.pack();
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MyConceptPane.java
 *
 * Created on 2010-12-12, 18:52:55
 */
package lp;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

/**
 *
 * @author Shuaiguo
 */
public class MyConceptPane extends javax.swing.JPanel {

    /** Creates new form MyConceptPane */
    public MyConceptPane() {
        initComponents();
    }

    public void updateTable() {
        DefaultTableModel model;

        model = (DefaultTableModel) mcTable.getModel();

        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }

        ArrayList<EPerformance> el = LPApp.lpModel.getEPerformances(LPApp.getApplication().user.learner);

        for (EPerformance er : el) {
            Object[] oa = {er.getConcept().getName(), er.getValue()};
            model.addRow(oa);
        }

        model = (DefaultTableModel) erTable.getModel();

        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }

        ArrayList<EPortfolio> eps = LPApp.lpModel.getEPortfolios(new ELearner(LPApp.getApplication().user.username));

        ArrayList<EResource> erl = new ArrayList<EResource>();
        for(EPortfolio ep:eps){
          erl.add(ep.getEResource());
        }
        for (EResource er : erl) {
            Object[] oa = {er.getName(), er.getDifficulty(), new javax.swing.JLabel(util.Constant.SERVERTESTURL + "/resources/" + er.getRid())};
            model.addRow(oa);
        }

        mcTable.updateUI();
        erTable.updateUI();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mcTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        erTable = new javax.swing.JTable();

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mcTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "知识点", "学习效果"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mcTable.setName("mcTable"); // NOI18N
        jScrollPane1.setViewportView(mcTable);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(lp.LPApp.class).getContext().getResourceMap(MyConceptPane.class);
        mcTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("mcTable.columnModel.title0")); // NOI18N
        mcTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("mcTable.columnModel.title1")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        erTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "资源名", "难易程度", "下载"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        erTable.setName("erTable"); // NOI18N
        jScrollPane2.setViewportView(erTable);
        erTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("erTable.columnModel.title0")); // NOI18N
        erTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("erTable.columnModel.title1")); // NOI18N
        erTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("erTable.columnModel.title2")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable erTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable mcTable;
    // End of variables declaration//GEN-END:variables
}

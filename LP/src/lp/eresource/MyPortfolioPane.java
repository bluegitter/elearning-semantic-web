/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MyPortfolioPane.java
 *
 * Created on 2011-1-5, 19:33:00
 */
package lp.eresource;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import jena.impl.ELearnerModelImpl;
import lp.LPApp;
import lp.eresource.MyPortfolioActionListener;
import ontology.EConcept;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author ghh
 */
public class MyPortfolioPane extends javax.swing.JPanel {

    public ArrayList<EPortfolio> resourceList;
    private ELearner el;

    public static void main(String[] args) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        MyPortfolioPane uip = new MyPortfolioPane();
        f.add(uip);
        f.pack();
        f.setVisible(true);
    }

    /** Creates new form MyPortfolioPane */
    public MyPortfolioPane() {
        initComponents();
        myInit();
    }

    private void myInit() {
        el = LPApp.lpModel.getELearner("el001");
        updateResourceTable();
        resources.addMouseListener(new MyPortfolioActionListener(this));
    }

    public void updateResourceTable() {
        //initial concepts in performance for user
        DefaultTableModel model = (DefaultTableModel) resources.getModel();
        for (int index = model.getRowCount() - 1; index >= 0; index--) {
            model.removeRow(index);
        }
        resourceList = LPApp.lpModel.getEPortfolios(el);
        for (int i = 0; i < resourceList.size(); i++) {
            EPortfolio f = resourceList.get(i);
            ISCB_Resource res = f.getEResource();
            ArrayList<EConcept> con = LPApp.lpModel.getEConcepts(res);
            String s = con.size() + "个知识点";
            String userRate = "点击评价";
            if (f.getRate() != 0) {
                userRate = "资源评分：" + f.getRate();
            }
            Object[] oa = {res.getName(), res.getDifficulty(), userRate, s};
            model.addRow(oa);
        }
        resources.updateUI();
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
        resources = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        resources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "资源名称", "资源难度", "资源评价", "关联知识点数"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resources.setName("resources"); // NOI18N
        jScrollPane1.setViewportView(resources);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(MyPortfolioPane.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable resources;
    // End of variables declaration//GEN-END:variables
}

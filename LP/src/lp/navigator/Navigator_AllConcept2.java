/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Navigator_AllConcept2.java
 *
 * Created on 2011-1-15, 2:56:00
 */

package lp.navigator;

import javax.swing.JButton;
import lp.LPApp;

/**
 *
 * @author ghh
 */
public class Navigator_AllConcept2 extends javax.swing.JPanel {
private NavigatorDialog parent;
    /** Creates new form Navigator_AllConcept2 */
    public Navigator_AllConcept2(NavigatorDialog parent1) {
        initComponents();
        this.parent = parent1;
       
        parent.setSize(637, 478);
    }

     private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.parent.setNext();
}
     private void ignorButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
         parent.setVisible(false);
         parent.dispose();
}

          private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.parent.setPrevious();
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(637, 478));
        setMinimumSize(new java.awt.Dimension(637, 478));

        JButton nextButton = new JButton();
        nextButton.setBounds(507, 435,120,33); 
        nextButton.setText("下一步");
        this.add(nextButton);
        nextButton.grabFocus();
        nextButton.setVisible(true);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        JButton ignorButton = new JButton();
        ignorButton.setBounds(10, 435,120,33); 
        ignorButton.setText("跳过向导");
        this.add(ignorButton);
        ignorButton.setVisible(true);
        ignorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ignorButtonActionPerformed(evt);
            }
        });

        JButton previousButton = new JButton();
        previousButton.setBounds(377, 435,120,33); 
        previousButton.setText("上一步");
        this.add(previousButton);
        previousButton.setVisible(true);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        this.setSize(637, 478);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lp/resources/6.png"))); // NOI18N
        jLabel3.setText("浏览某知识点的相应资源，学习资源并进行评价");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lp/resources/evaluation.png"))); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(550, 402));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RadarPanel.java
 *
 * Created on 2010-12-20, 14:38:01
 */
package lp.eresource;

import exception.jena.IndividualNotExistException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JSlider;
import lp.AssessmentPane;
import lp.LPApp;
import lp.UserProfilePane;
import ontology.EPerformance;
import ontology.EPerformanceAssessment;

/**
 *
 * @author David
 */
public class RadarPanel extends javax.swing.JPanel {

    private EPerformance perform;

    /** Creates new form RadarPanel */
    public RadarPanel(EPerformance perform) {
        this.perform = perform;
        initComponents();
        for (int i = 0; i < 6; i++) {
            mx1[i] = cx - 0.25 * (cx - x[i]);
            my1[i] = cy - 0.25 * (cy - y[i]);
            mx2[i] = cx - 0.5 * (cx - x[i]);
            my2[i] = cy - 0.5 * (cy - y[i]);
            mx3[i] = cx - 0.75 * (cx - x[i]);
            my3[i] = cy - 0.75 * (cy - y[i]);
        }
        jSlider1.setValue(Integer.parseInt(perform.assessment.a1));
        jSlider2.setValue(Integer.parseInt(perform.assessment.a2));
        jSlider3.setValue(Integer.parseInt(perform.assessment.a3));
        jSlider4.setValue(Integer.parseInt(perform.assessment.a4));
        jSlider5.setValue(Integer.parseInt(perform.assessment.a5));
        jSlider6.setValue(Integer.parseInt(perform.assessment.a6));
        jSlide1Value.setText(jSlider1.getValue() + "");
        jSlide2Value.setText(jSlider2.getValue() + "");
        jSlide3Value.setText(jSlider3.getValue() + "");
        jSlide4Value.setText(jSlider4.getValue() + "");
        jSlide5Value.setText(jSlider5.getValue() + "");
        jSlide6Value.setText(jSlider6.getValue() + "");
    }
    private double tp = 150.0 / Math.sqrt(3);
    private double[] x = {56, 56, 206, 356, 356, 206};
    private double[] y = {550 - 3 * tp, 550 - tp, 550, 550 - tp, 550 - tp * 3, 550 - tp * 4};
    double[] mx1 = new double[6], my1 = new double[6], mx2 = new double[6], my2 = new double[6], mx3 = new double[6], my3 = new double[6];
    private static BasicStroke B_STROKE = new BasicStroke(2.0f);
    private static BasicStroke R_STROKE = new BasicStroke(5.0f);
    private static BasicStroke D_STROKE = new BasicStroke(1.5f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_ROUND,
            6.0f,
            new float[]{3.0f, 3.0f},
            1.5f);
    private double cx = 206, cy = 550 - 2 * tp;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.red);
        g2d.setStroke(B_STROKE);

        for (int i = 0; i < 5; i++) {
            g2d.draw(new Line2D.Double(x[i], y[i], x[i + 1], y[i + 1]));
        }
        g2d.draw(new Line2D.Double(x[5], y[5], x[0], y[0]));

        g2d.setStroke(D_STROKE);

        for (int i = 0; i < 5; i++) {
            g2d.draw(new Line2D.Double(cx, cy, x[i], y[i]));
            g2d.draw(new Line2D.Double(mx1[i], my1[i], mx1[i + 1], my1[i + 1]));
            g2d.draw(new Line2D.Double(mx2[i], my2[i], mx2[i + 1], my2[i + 1]));
            g2d.draw(new Line2D.Double(mx3[i], my3[i], mx3[i + 1], my3[i + 1]));
        }
        g2d.draw(new Line2D.Double(cx, cy, x[5], y[5]));
        g2d.draw(new Line2D.Double(mx1[5], my1[5], mx1[0], my1[0]));
        g2d.draw(new Line2D.Double(mx2[5], my2[5], mx2[0], my2[0]));
        g2d.draw(new Line2D.Double(mx3[5], my3[5], mx3[0], my3[0]));

        g2d.setColor(Color.black);
        g2d.setStroke(R_STROKE);

        double[] nx = new double[6];
        double[] ny = new double[6];
        JSlider[] sa = {jSlider1, jSlider2, jSlider3, jSlider4, jSlider5, jSlider6};
        for (int i = 0; i < 6; i++) {
            JSlider s = sa[i];
            nx[i] = cx - s.getValue() / 100.0 * (cx - x[i]);
            ny[i] = cy - s.getValue() / 100.0 * (cy - y[i]);
        }

        for (int i = 0; i < 5; i++) {
            g2d.draw(new Line2D.Double(nx[i], ny[i], nx[i + 1], ny[i + 1]));
        }
        g2d.draw(new Line2D.Double(nx[5], ny[5], nx[0], ny[0]));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jSlider2 = new javax.swing.JSlider();
        jSlider3 = new javax.swing.JSlider();
        jSlider4 = new javax.swing.JSlider();
        jSlider5 = new javax.swing.JSlider();
        jSlider6 = new javax.swing.JSlider();
        jButton1 = new javax.swing.JButton();
        jSlide1Value = new javax.swing.JLabel();
        jSlide2Value = new javax.swing.JLabel();
        jSlide3Value = new javax.swing.JLabel();
        jSlide4Value = new javax.swing.JLabel();
        jSlide5Value = new javax.swing.JLabel();
        jSlide6Value = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(411, 500));
        setMinimumSize(new java.awt.Dimension(411, 500));
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(RadarPanel.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jSlider1.setName("jSlider1"); // NOI18N
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jSlider2.setName("jSlider2"); // NOI18N
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jSlider3.setName("jSlider3"); // NOI18N
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jSlider4.setName("jSlider4"); // NOI18N
        jSlider4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jSlider5.setName("jSlider5"); // NOI18N
        jSlider5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jSlider6.setName("jSlider6"); // NOI18N
        jSlider6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repaintRadar(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSlide1Value.setText(resourceMap.getString("jSlide1Value.text")); // NOI18N
        jSlide1Value.setName("jSlide1Value"); // NOI18N

        jSlide2Value.setText(resourceMap.getString("jSlide2Value.text")); // NOI18N
        jSlide2Value.setName("jSlide2Value"); // NOI18N

        jSlide3Value.setText(resourceMap.getString("jSlide3Value.text")); // NOI18N
        jSlide3Value.setName("jSlide3Value"); // NOI18N

        jSlide4Value.setText(resourceMap.getString("jSlide4Value.text")); // NOI18N
        jSlide4Value.setName("jSlide4Value"); // NOI18N

        jSlide5Value.setText(resourceMap.getString("jSlide5Value.text")); // NOI18N
        jSlide5Value.setName("jSlide5Value"); // NOI18N

        jSlide6Value.setText(resourceMap.getString("jSlide6Value.text")); // NOI18N
        jSlide6Value.setName("jSlide6Value"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addComponent(jLabel5))
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSlider6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSlide2Value)
                                .addComponent(jSlide1Value))
                            .addComponent(jSlide3Value)
                            .addComponent(jSlide4Value)
                            .addComponent(jSlide5Value))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jSlide6Value))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jSlide1Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlide2Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlide3Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlide4Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlide5Value, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlide6Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(386, 386, 386))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void repaintRadar(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_repaintRadar
        this.repaint();
        jSlide1Value.setText(jSlider1.getValue() + "");
        jSlide2Value.setText(jSlider2.getValue() + "");
        jSlide3Value.setText(jSlider3.getValue() + "");
        jSlide4Value.setText(jSlider4.getValue() + "");
        jSlide5Value.setText(jSlider5.getValue() + "");
        jSlide6Value.setText(jSlider6.getValue() + "");
    }//GEN-LAST:event_repaintRadar

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println(" submit");
        String a1 = jSlide1Value.getText();
        String a2 = jSlide2Value.getText();
        String a3 = jSlide3Value.getText();
        String a4 = jSlide4Value.getText();
        String a5 = jSlide5Value.getText();
        String a6 = jSlide6Value.getText();
        EPerformanceAssessment assessment = new EPerformanceAssessment(a1, a2, a3, a4, a5, a6);
        try {
            LPApp.lpModel.updateEPerformanceAssessment(perform, assessment);
            UserProfilePane userProfilePane = (UserProfilePane) LPApp.getApplication().view.getPanes()[LPApp.PROFILE];
            userProfilePane.updateUserProfilePane();
            AssessmentPane assessmentPane =(AssessmentPane) LPApp.getApplication().view.getPanes()[LPApp.ASSESSMENT];
            assessmentPane.perform.updatePerformanceTable();
        } catch (IndividualNotExistException ex) {
            Logger.getLogger(RadarPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSlide1Value;
    private javax.swing.JLabel jSlide2Value;
    private javax.swing.JLabel jSlide3Value;
    private javax.swing.JLabel jSlide4Value;
    private javax.swing.JLabel jSlide5Value;
    private javax.swing.JLabel jSlide6Value;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JSlider jSlider4;
    private javax.swing.JSlider jSlider5;
    private javax.swing.JSlider jSlider6;
    // End of variables declaration//GEN-END:variables
}

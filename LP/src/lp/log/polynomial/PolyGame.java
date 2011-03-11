/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PolyGame.java
 *
 * Created on 2011-3-8, 23:48:44
 */
package lp.log.polynomial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * 6,5,9,3 = 3
 * @author william
 */
public class PolyGame extends javax.swing.JPanel {

    public static void main(String[] args) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        PolyGame rt = new PolyGame();
        f.setTitle("四则运算小游戏");
        f.add(rt);
        f.pack();
        f.setVisible(true);
    }

    /** Creates new form PolyGame */
    public PolyGame() {
        initComponents();
        displayArea = new JTextArea();
        jScrollPane1.setViewportView(displayArea);
        myPoly = new MyPolyomial();
        updatePanel();
        myInit();
    }
    private Date now = new Date(0);
    private void myInit() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date now2 = new Date(now.getTime() + 1000);
                now = now2;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                jLabel7.setText(formatter.format(now));
            }
        });
        timer.start();


    }

    private void updatePanel() {
        myPoly.a = getRandomNum();
        jLabel1.setText(myPoly.a + "");
        myPoly.b = getRandomNum();
        jLabel2.setText(myPoly.b + "");
        myPoly.c = getRandomNum();
        jLabel3.setText(myPoly.c + "");
        myPoly.d = getRandomNum();
        jLabel4.setText(myPoly.d + "");
        myPoly.result = getRandomNum();
        jLabel5.setText(myPoly.result + "");
        myPoly.s1 = '+';
        myPoly.s2 = '+';
        myPoly.s3 = '+';
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);

    }

    private int getRandomNum() {
        Random ran = new Random();
        return ran.nextInt(11);
    }
    private MyPolyomial myPoly;
    public char s[] = {'+', '-', '*', '/'};
    public JTextArea displayArea;

    public void getResult(int a, int b, int c, int d, int result) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    myPoly.s1 = s[i];
                    myPoly.s2 = s[j];
                    myPoly.s3 = s[k];
                    if (myPoly.isAnswer()) {
                        String rr = a + "" + s[i] + "" + b + "" + s[j] + "" + c + "" + s[k] + "" + d + "=" + result;
                        sb.append(rr + "\n");
                        System.out.println(rr);
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            sb.append("没有结果");
        }
        displayArea.setText(sb.toString());
    }

    public boolean hasResult(int a, int b, int c, int d, int result) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    myPoly.s1 = s[i];
                    myPoly.s2 = s[j];
                    myPoly.s3 = s[k];
                    if (myPoly.isAnswer()) {
                        String rr = a + "" + s[i] + "" + b + "" + s[j] + "" + c + "" + s[k] + "" + d + "=" + result;
                        System.out.println(rr);
                        return true;
                    }
                }
            }
        }
        return false;
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
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel1.setText("a");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel2.setText("b");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel3.setText("c");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel4.setText("d");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel5.setText("result");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(new java.awt.Font("宋体", 1, 18));
        jLabel6.setText("=");
        jLabel6.setName("jLabel6"); // NOI18N

        jComboBox1.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-", "*", "/" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jComboBox2.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-", "*", "/" }));
        jComboBox2.setName("jComboBox2"); // NOI18N

        jComboBox3.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-", "*", "/" }));
        jComboBox3.setName("jComboBox3"); // NOI18N

        jLabel7.setFont(new java.awt.Font("宋体", 1, 18)); // NOI18N
        jLabel7.setText("TIME");
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jButton4.setText("再来一次");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                againAction(evt);
            }
        });

        jButton3.setText("帮助");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpAction(evt);
            }
        });

        jButton2.setText("无答案");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noAnswerAction(evt);
            }
        });

        jButton1.setText("提交");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitAction(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(34, 34, 34)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel3)
                                .addGap(26, 26, 26)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(26, 26, 26)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitAction
        // TODO add your handling code here:
        myPoly.s1 = jComboBox1.getSelectedItem().toString().toCharArray()[0];
        myPoly.s2 = jComboBox2.getSelectedItem().toString().toCharArray()[0];
        myPoly.s3 = jComboBox3.getSelectedItem().toString().toCharArray()[0];
        if (myPoly.isAnswer()) {
            displayArea.setText("恭喜你回答正确拉");
            updatePanel();
        }
    }//GEN-LAST:event_submitAction

    private void noAnswerAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noAnswerAction
        // TODO add your handling code here:
        if (hasResult(myPoly.a, myPoly.b, myPoly.c, myPoly.d, myPoly.result)) {
            displayArea.setText("有正确答案的!!");
        } else {
            displayArea.setText("聪明!!重新来吧");
            updatePanel();
        }
    }//GEN-LAST:event_noAnswerAction

    private void helpAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpAction
        // TODO add your handling code here:
        getResult(myPoly.a, myPoly.b, myPoly.c, myPoly.d, myPoly.result);
    }//GEN-LAST:event_helpAction

    private void againAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_againAction
        // TODO add your handling code here:
        updatePanel();
        displayArea.setText("");
    }//GEN-LAST:event_againAction
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
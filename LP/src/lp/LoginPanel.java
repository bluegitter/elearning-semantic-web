package lp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

/**
 *
 * @author Shuaiguo
 */
public class LoginPanel extends javax.swing.JPanel {

    /** Creates new form LoginPanel */
    public LoginPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username = new javax.swing.JTextField();
        regBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tipLabel = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();

        setName("Form"); // NOI18N

        username.setName("username"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(lp.LPApp.class).getContext().getResourceMap(LoginPanel.class);
        regBtn.setText(resourceMap.getString("regBtn.text")); // NOI18N
        regBtn.setName("regBtn"); // NOI18N
        regBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regBtnActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        loginBtn.setText(resourceMap.getString("loginBtn.text")); // NOI18N
        loginBtn.setName("loginBtn"); // NOI18N
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        tipLabel.setFont(resourceMap.getFont("tipLabel.font")); // NOI18N
        tipLabel.setText(resourceMap.getString("tipLabel.text")); // NOI18N
        tipLabel.setName("tipLabel"); // NOI18N

        password.setText(resourceMap.getString("password.text")); // NOI18N
        password.setName("password"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(loginBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(regBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(password)
                                    .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(tipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn)
                    .addComponent(regBtn))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        final LPView view = LPApp.getApplication().view;
        LPApp.getApplication().user = new EUser(username.getText());
        view.setBusy("正在验证密码...");
        Thread authThread = new Thread() {

            @Override
            public void run() {
                boolean loginAuth = LPApp.getApplication().user.login(new String(password.getPassword()));
                if (loginAuth) {
                    LPApp.getApplication().user.learner = LPApp.lpModel.getELearner(LPApp.getApplication().user.username);
                    view.setBusy("正在加载数据...");
                    view.initTools();
                } else {
                    tipLabel.setText("登录失败");
                    username.grabFocus();
                }
                view.setIdle();
            }
        };
        authThread.start();
}//GEN-LAST:event_loginBtnActionPerformed

    private void regBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regBtnActionPerformed

//            LPApp.getApplication().view.setMainTool(LPApp.REGIST);
        JDialog d = new NavigatorDialog(LPApp.getApplication().getMainFrame());
        d.setTitle("注册向导");
        d.setModal(true);
        d.pack();
        d.setVisible(true);
    }//GEN-LAST:event_regBtnActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton regBtn;
    private javax.swing.JLabel tipLabel;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}

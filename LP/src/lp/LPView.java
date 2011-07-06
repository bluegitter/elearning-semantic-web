/*
 * LPView.java
 */
package lp;

import lp.navigator.Navi_InterestPane;
import lp.navigator.NavigatorDialog;
import lp.navigator.NavigatorPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lp.map.MapBg;

/**
 * The application's main frame.
 */
public class LPView extends FrameView {

    private javax.swing.JPanel[] panes;

    public JPanel[] getPanes() {
        return panes;
    }

    public LPView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    public void setBusy(String status) {
        if (!busyIconTimer.isRunning()) {
//            System.out.println(1);
            statusAnimationLabel.setIcon(busyIcons[0]);
            busyIconIndex = 0;
            busyIconTimer.start();
        }
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);
        statusMessageLabel.setText(status);
        this.mainPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
    }

    public void setIdle() {
        statusMessageLabel.setText("");
        busyIconTimer.stop();
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        progressBar.setValue(0);
        this.mainPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = LPApp.getApplication().getMainFrame();
            aboutBox = new LPAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        LPApp.getApplication().show(aboutBox);
    }

    public void initTools() {

        javax.swing.JPanel[] pa = {new lp.display.MyConceptDemo(), new AllConceptPane(), new RecommendContainer(),
            new SearchPane(), new UserProfilePane(), new AssessmentPane(), new MapBg(mainPanel), new JPanel()};
        panes = pa;

        setMainTool(LPApp.MY_CONCEPT);
        lpToolBar.setVisible(true);
        jToggleButton1.setSelected(true);
    }

    public void setBtnSelected() {
        jToggleButton6.setSelected(true);
    }

    public void setMainTool(int i) {
        LPApp.TOOL_STATE = i;
        mainPanel.removeAll();
        mainPanel.add(panes[i]);

        this.mainPanel.updateUI();

        if (i == LPApp.RECOMMEND) {
            ((RecommendContainer) this.panes[i]).renew();
        } else if (i == LPApp.MY_CONCEPT) {
            if (mcdshow) {
                ((lp.display.MyConceptDemo) this.panes[i]).renew();
            } else {
                mcdshow = true;
            }
        }


        if (i == LPApp.PROFILE) {
            ((UserProfilePane) this.panes[i]).updateUserProfilePane();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        lpToolBar = new javax.swing.JToolBar();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        buttonGroup1 = new javax.swing.ButtonGroup();

        mainPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 662, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        lpToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lpToolBar.setFloatable(false);
        lpToolBar.setMaximumSize(new java.awt.Dimension(624, 90));
        lpToolBar.setMinimumSize(new java.awt.Dimension(624, 90));
        lpToolBar.setName("lpToolBar"); // NOI18N
        lpToolBar.setPreferredSize(new java.awt.Dimension(624, 90));

        buttonGroup1.add(jToggleButton1);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(LPView.class);
        jToggleButton1.setFont(resourceMap.getFont("jToggleButton1.font")); // NOI18N
        jToggleButton1.setIcon(resourceMap.getIcon("jToggleButton1.icon")); // NOI18N
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setToolTipText(resourceMap.getString("jToggleButton1.toolTipText")); // NOI18N
        jToggleButton1.setActionCommand(resourceMap.getString("jToggleButton1.actionCommand")); // NOI18N
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton1.setName("jToggleButton1"); // NOI18N
        jToggleButton1.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton1);

        buttonGroup1.add(jToggleButton2);
        jToggleButton2.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton2.setIcon(resourceMap.getIcon("jToggleButton2.icon")); // NOI18N
        jToggleButton2.setText(resourceMap.getString("jToggleButton2.text")); // NOI18N
        jToggleButton2.setFocusable(false);
        jToggleButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton2.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton2.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton2.setName("jToggleButton2"); // NOI18N
        jToggleButton2.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton2);

        buttonGroup1.add(jToggleButton3);
        jToggleButton3.setFont(resourceMap.getFont("jToggleButton3.font")); // NOI18N
        jToggleButton3.setIcon(resourceMap.getIcon("jToggleButton3.icon")); // NOI18N
        jToggleButton3.setText(resourceMap.getString("jToggleButton3.text")); // NOI18N
        jToggleButton3.setFocusable(false);
        jToggleButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton3.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton3.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton3.setName("jToggleButton3"); // NOI18N
        jToggleButton3.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton3);

        buttonGroup1.add(jToggleButton4);
        jToggleButton4.setFont(resourceMap.getFont("jToggleButton4.font")); // NOI18N
        jToggleButton4.setIcon(resourceMap.getIcon("jToggleButton4.icon")); // NOI18N
        jToggleButton4.setText(resourceMap.getString("jToggleButton4.text")); // NOI18N
        jToggleButton4.setFocusable(false);
        jToggleButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton4.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton4.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton4.setName("jToggleButton4"); // NOI18N
        jToggleButton4.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton4);

        buttonGroup1.add(jToggleButton5);
        jToggleButton5.setFont(resourceMap.getFont("jToggleButton5.font")); // NOI18N
        jToggleButton5.setIcon(resourceMap.getIcon("jToggleButton5.icon")); // NOI18N
        jToggleButton5.setActionCommand(resourceMap.getString("jToggleButton5.actionCommand")); // NOI18N
        jToggleButton5.setFocusable(false);
        jToggleButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton5.setLabel(resourceMap.getString("jToggleButton5.label")); // NOI18N
        jToggleButton5.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton5.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton5.setName("jToggleButton5"); // NOI18N
        jToggleButton5.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton5);

        buttonGroup1.add(jToggleButton6);
        jToggleButton6.setFont(resourceMap.getFont("jToggleButton6.font")); // NOI18N
        jToggleButton6.setIcon(resourceMap.getIcon("jToggleButton6.icon")); // NOI18N
        jToggleButton6.setText(resourceMap.getString("jToggleButton6.text")); // NOI18N
        jToggleButton6.setFocusable(false);
        jToggleButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton6.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton6.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton6.setName("jToggleButton6"); // NOI18N
        jToggleButton6.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton6);

        buttonGroup1.add(jToggleButton7);
        jToggleButton7.setFont(resourceMap.getFont("jToggleButton6.font")); // NOI18N
        jToggleButton7.setIcon(resourceMap.getIcon("jToggleButton7.icon")); // NOI18N
        jToggleButton7.setText(resourceMap.getString("jToggleButton7.text")); // NOI18N
        jToggleButton7.setActionCommand(resourceMap.getString("jToggleButton7.actionCommand")); // NOI18N
        jToggleButton7.setFocusable(false);
        jToggleButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton7.setMaximumSize(new java.awt.Dimension(103, 90));
        jToggleButton7.setMinimumSize(new java.awt.Dimension(103, 90));
        jToggleButton7.setName("jToggleButton7"); // NOI18N
        jToggleButton7.setPreferredSize(new java.awt.Dimension(103, 90));
        jToggleButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton7ActionPerformed(evt);
            }
        });
        lpToolBar.add(jToggleButton7);

        setComponent(mainPanel);
        setStatusBar(statusPanel);
        setToolBar(lpToolBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        setMainTool(LPApp.MY_CONCEPT);
}//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        setMainTool(LPApp.ALL_CONCEPT);
}//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        setMainTool(LPApp.RECOMMEND);
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        setMainTool(LPApp.SEARCH);
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        setMainTool(LPApp.PROFILE);

    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        // TODO add your handling code here:
        setMainTool(LPApp.ASSESSMENT);
        ((AssessmentPane) panes[LPApp.ASSESSMENT]).updateAssessmentPane();

    }//GEN-LAST:event_jToggleButton6ActionPerformed

    private void jToggleButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton7ActionPerformed
        setMainTool(LPApp.NEW_MAP);
    }//GEN-LAST:event_jToggleButton7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    public javax.swing.JToolBar lpToolBar;
    public javax.swing.JPanel mainPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    public NavigatorPane np = null;
    public Navi_InterestPane rp = null;
    private boolean mcdshow = false;
}

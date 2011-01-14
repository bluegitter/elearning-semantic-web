/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NavigatorPane.java
 *
 * Created on 2010-12-30, 15:17:28
 */

package lp;

import exception.jena.IndividualExistException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import jena.impl.ELearnerModelImpl;
import lp.display.mytree.CheckNode;
import lp.display.mytree.CheckRenderer;
import ontology.EConcept;
import ontology.EInterest;

/**
 *
 * @author ghh
 */
public class NavigatorPane extends javax.swing.JPanel {
    public ArrayList selectedNodes = new ArrayList();
    private CheckNode root = null;
    private NavigatorDialog parent;
    public ArrayList<EInterest>  EInterests = new ArrayList<EInterest>();
    /** Creates new form NavigatorPane */
    public NavigatorPane(NavigatorDialog parent) {
        this.parent = parent;
       initComponents();
       
    }


    public EConcept getRootConcept() {
        EConcept rootConcept = new EConcept();
        rootConcept.setCid("Computer_Science");
        rootConcept.setName("软件工程");
        return rootConcept;
    }

    private CheckNode addNodes(CheckNode n, EConcept r) {

        ArrayList<EConcept> a = LPApp.getApplication().lpModel.getSonConcepts(r);
        for (EConcept c : a) {
            //System.out.println(c.getCid() + c.getName() + "hahahahahaha");
            CheckNode child = new CheckNode(c);
            n.add(child);
            addNodes(child, c);
        }
        return n;
    }

    class NodeSelectionListener extends MouseAdapter {

        JTree tree;

        NodeSelectionListener(JTree tree) {
            this.tree = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int row = tree.getRowForLocation(x, y);
            TreePath path = tree.getPathForRow(row);
            //TreePath  path = tree.getSelectionPath();
            if (path != null) {
                CheckNode node = (CheckNode) path.getLastPathComponent();
                boolean isSelected = !(node.isSelected());
                node.setSelected(isSelected);
                if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
                    if (isSelected) {
                        tree.expandPath(path);
                    } else {
                        tree.collapsePath(path);
                    }
                }
                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                // I need revalidate if node is root.  but why?
                if (row == 0) {
                    tree.revalidate();
                    tree.repaint();
                }
            }
            selectedNodes = new ArrayList();
            getUserSelect(root);
            for(int i=0;i<selectedNodes.size();i++)
                System.out.print(selectedNodes.get(i)+" ");
            System.out.println("");
            this.tree.repaint();
        }
    }
/*
    class ModePanel extends JPanel implements ActionListener {

        CheckNode nodes2[];
        JRadioButton b_single, b_dig_in;

        ModePanel(CheckNode[] nodes2) {
            System.arraycopy(nodes, 0, nodes2, 0, nodes.length);

            setLayout(new GridLayout(2, 1));
            setBorder(new TitledBorder("Check Mode"));
            ButtonGroup group = new ButtonGroup();
            add(b_dig_in = new JRadioButton("DIG_IN  "));
            add(b_single = new JRadioButton("SINGLE  "));
            group.add(b_dig_in);
            group.add(b_single);
            b_dig_in.addActionListener(this);
            b_single.addActionListener(this);
            b_dig_in.setSelected(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int mode;
            if (b_single == e.getSource()) {
                mode = CheckNode.SINGLE_SELECTION;
            } else {
                mode = CheckNode.DIG_IN_SELECTION;
            }
            for (int i = 0; i < nodes.length; i++) {
                //   nodes.setSelectionMode(mode);
                nodes[i].setSelected(Boolean.parseBoolean(mode + ""));
            }
        }
    }
*/
    class ButtonActionListener implements ActionListener {

        CheckNode root;
        JTextArea textArea;

        ButtonActionListener(final CheckNode root,
                final JTextArea textArea) {
            this.root = root;
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Enumeration enumOne = root.breadthFirstEnumeration();
            while (enumOne.hasMoreElements()) {
                CheckNode node = (CheckNode) enumOne.nextElement();
                if (node.isSelected()) {
                    TreeNode[] nodes = node.getPath();
                    textArea.append("\n" + nodes[0].toString());
                    for (int i = 1; i < nodes.length; i++) {
                        textArea.append("/" + nodes.toString());
                    }
                }
            }
        }
    }
    //这个方法是用来返回一个用户所选择的树形结构

    public CheckNode rebuildTree(CheckNode userRoot, CheckNode userNode) {
        if (userNode.getChildCount() == 0) {
            if (userNode == userRoot) {
                return null;
            }
            if (!userNode.isSelected()) {
                userNode.removeFromParent();
            }
        } else if (userNode.getChildCount() > 0) {

                for (int i = 0; i < userNode.getChildCount(); i++) {
                    rebuildTree(userRoot, (CheckNode) userNode.getChildAt(i));
                }

        }

        return userNode;
    }
    //打印一个根结点，也就是树

    public void printNode(CheckNode node) {
        if (node.getChildCount() == 0) {
            System.out.print(node.toString() + " ");
        } else if (node.getChildCount() > 0) {
            for (int i = 0; i < node.getChildCount(); i++) {
                printNode((CheckNode) node.getChildAt(i));
            }
        }
    }
    //存储用户选择的节点
    public void getUserSelect(CheckNode node){
        //ArrayList result = new ArrayList();
        EConcept ec = (EConcept)node.getUserObject();
       // LPApp.lpModel.getInterestConcepts(LPApp.getApplication().user.learner).remove(ec);
        selectedNodes.remove(ec.getCid());
        if (node.getChildCount() == 0&&node.isSelected()) {           
            selectedNodes.add(ec.getCid());
        } else if (node.getChildCount() > 0) {
            if(node.isSelected())
                selectedNodes.add(ec.getCid());
            for (int i = 0; i < node.getChildCount(); i++) {
                getUserSelect((CheckNode) node.getChildAt(i));
            }
        }
        //return result;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        EConcept cn = this.getRootConcept();
        root = addNodes(new CheckNode(cn), cn);
        JTree tree = new JTree(root);
        //use our CheckRenderer
        tree.setCellRenderer(new CheckRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(new NodeSelectionListener(tree));
        jScrollPane1 = new JScrollPane(tree);

        jDialog1.setName("jDialog1"); // NOI18N

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(lp.LPApp.class).getContext().getResourceMap(NavigatorPane.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setLabel(resourceMap.getString("jButton2.label")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setLabel(resourceMap.getString("jButton3.label")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jButton3.getAccessibleContext().setAccessibleName(resourceMap.getString("jButton3.AccessibleContext.accessibleName")); // NOI18N
        jButton4.getAccessibleContext().setAccessibleName(resourceMap.getString("jButton4.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
 
       
        for (int i =0;i<this.selectedNodes.size();i++)
        {
            EInterest ei = new EInterest();
            String cid = selectedNodes.get(i).toString();
            ei.setEConcept(LPApp.lpModel.getEConcept(cid));
            ei.setELearner(LPApp.getApplication().user.learner);
            ei.setId("newinterest"+LPApp.getApplication().user.learner.getId()+i);
            ei.setValue(0.5f);
            try {
                LPApp.lpModel.addEInterest(ei);
            } catch (IndividualExistException ex) {
                Logger.getLogger(NavigatorPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            EInterests.add(ei);
        }

       // parent.nodes = this.selectedNodes;

        parent.setNext(EInterests);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        parent.setPrevious();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables


}

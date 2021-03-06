/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NavigatorPane.java
 *
 * Created on 2010-12-30, 15:17:28
 */

package lp.navigator;

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
import lp.LPApp;
import lp.display.mytree.CheckNode;
import lp.display.mytree.CheckRenderer;
import ontology.EConcept;
import ontology.EInterest;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author ghh
 */
public class NavigatorPane extends javax.swing.JPanel {
    public ArrayList selectedNodes = new ArrayList();
    public CheckNode root = null;
    private NavigatorDialog parent;
    public ArrayList<EInterest>  EInterests = new ArrayList<EInterest>();
    /** Creates new form NavigatorPane */
    public NavigatorPane(NavigatorDialog parent) {
        this.parent = parent;
        parent.setTitle("初始化向导：选择感兴趣的知识点");
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
        ArrayList<ISCB_Resource> rList = new  ArrayList<ISCB_Resource>();//LPApp.getApplication().lpModel.getEResourcesByEConcept(r)
        for (EConcept c : a) {
            //System.out.println(c.getCid() + c.getName() + "hahahahahaha");
            rList = LPApp.getApplication().lpModel.getEResourcesByEConcept(c);
            CheckNode child = null;
            if(rList.size() > 0)
            {
             child = new CheckNode(c);
             n.add(child);
             addNodes(child, c);
            }
           
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
    
      private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {

        for (int i =0;i<this.selectedNodes.size();i++) {
            EInterest ei = new EInterest();
            String cid = selectedNodes.get(i).toString();
            ei.setEConcept(LPApp.lpModel.getEConcept(cid));
            ei.setELearner(LPApp.getApplication().user.learner);
            ei.setId("interest_"+LPApp.getApplication().user.learner.getId()+"_"+cid);
            ei.setValue(0.5f);
            EInterests.add(ei);
        }
        parent.setNext(EInterests);
}

     private void ignorButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
         parent.setVisible(false);
         parent.dispose();
}

          private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
       // selectedNodes = new ArrayList();
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

        jDialog1 = new javax.swing.JDialog();
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
        previousButton.setEnabled(false);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables


}

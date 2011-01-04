package lp.display.mytree;

import com.hp.hpl.jena.graph.Node;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import jena.impl.ELearnerModelImpl;
import lp.display.mytree.CheckNode;
import lp.display.mytree.CheckRenderer;
import ontology.EConcept;

public final class TestFrame extends JFrame {
    //  public String strs[] = new String[5];

    public ArrayList selectedNodes = new ArrayList();
    public ELearnerModelImpl lpModel = new ELearnerModelImpl(new java.io.File("test\\owl\\elearning.owl"));
    public CheckNode[] nodes = new CheckNode[5];
    //  public EConcept[]enodes = new EConcept[3000];
    CheckNode root = null;

    public TestFrame() {
        super("CheckNode TreeExample");
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//当前系统风格
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");//Motif风格，是蓝黑
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//跨平台的Java风格
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//windows风格,在linux下运行时不成功
            //UIManager.setLookAndFeel("javax.swing.plaf.windows.WindowsLookAndFeel");//windows风格
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");//java风格
            //UIManager.setLookAndFeel("com.apple.mrj.swing.MacLookAndFeel");//待考察，

        } catch (Exception e) {
            e.printStackTrace();
        }
        EConcept cn = this.getRootConcept();

        root = addNodes(new CheckNode(cn), cn);
        JTree tree = new JTree(root);

        //use our CheckRenderer
        tree.setCellRenderer(new CheckRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);


        tree.addMouseListener(new NodeSelectionListener(tree));

        JScrollPane sp = new JScrollPane(tree);
        ModePanel mp = new ModePanel(nodes);
        JTextArea textArea = new JTextArea(3, 10);
        JScrollPane textPanel = new JScrollPane(textArea);
        JButton button = new JButton("print");

        button.addActionListener(new ButtonActionListener(nodes[0], textArea));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mp, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.EAST);
        getContentPane().add(textPanel, BorderLayout.SOUTH);
    }

    public EConcept getRootConcept() {
        EConcept rootConcept = new EConcept();
        rootConcept.setCid("Software_Engineer");
        rootConcept.setName("software engineering");
        return rootConcept;
    }

    private CheckNode addNodes(CheckNode n, EConcept r) {

        ArrayList<EConcept> a = lpModel.getSonConcepts(r);
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
        if (node.getChildCount() == 0&&node.isSelected()) {
            selectedNodes.add(node.toString());
        } else if (node.getChildCount() > 0) {
            if(node.isSelected())
                selectedNodes.add(node.toString());
            for (int i = 0; i < node.getChildCount(); i++) {
                getUserSelect((CheckNode) node.getChildAt(i));
            }
        }
        //return result;
    }
    public static void main(String[] args) {
        // TODO 自动生成方法存根
        TestFrame frame = new TestFrame();
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(300, 200);
        frame.setBounds(200, 200, 300, 200);
        frame.setVisible(true);

    }
}

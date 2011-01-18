/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import lp.LPApp;
import ontology.EPerformance;
import ontology.resources.E_Resource;
import ontology.resources.ISCB_Resource;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.SearchTupleSet;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualItem;

/**
 *
 * @author David
 */
public class MyConceptDemo extends javax.swing.JPanel {

    private int[] last = new int[6];
    private int last_count = 0;

    public MyConceptDemo() {
        super(new BorderLayout());

        // create a new radial tree view
        final MyConceptDisplay gview = new MyConceptDisplay();
        Visualization vis = gview.getVisualization();

        gview.setBackground(this.getBackground());

        // create a search panel for the tree map
        SearchQueryBinding sq = new SearchQueryBinding(
                (Table) vis.getGroup(MyConceptDisplay.treeNodes), MyConceptDisplay.m_label,
                (SearchTupleSet) vis.getGroup(Visualization.SEARCH_ITEMS));
        JSearchPanel search = sq.createSearchPanel();
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 0));
        search.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 11));
        search.setBackground(this.getBackground());

        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        title.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 16));

        gview.addControlListener(new ControlAdapter() {

            @Override
            public void itemEntered(VisualItem item, MouseEvent e) {
                if (item.canGetString(MyConceptDisplay.m_label)) {
                    title.setText(item.getString(MyConceptDisplay.m_label));
                }
            }

            @Override
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
            }

            @Override
            public void itemClicked(VisualItem item, MouseEvent e) {
                EClass ec = (EClass) item.get(MyConceptDisplay.m_label);
                if (ec.isPerformance()) {
                    EPerformance ep = (EPerformance) ec.object;
                    ArrayList<ISCB_Resource> ra = LPApp.lpModel.getEResourcesByEConcept(ep.getConcept());
                    Node n = MyConceptDisplay.t.getNode(item.getRow());
                    for (int i = 0; i < last_count; i++) {
                        MyConceptDisplay.t.removeNode(last[i]);
                    }
                    int count = 0;
                    for (E_Resource r : ra) {
                        Node cn = MyConceptDisplay.t.addChild(n);
                        EClass tempclass = new EClass(r);
                        cn.set(MyConceptDisplay.m_label, tempclass);
                        cn.set(MyConceptDisplay.m_image_label, tempclass.getIconStr());
                        System.out.println(cn.getRow());
                        last[count] = cn.getRow();
                        if (++count > 5) {
                            break;
                        }
                    }
                    last_count = count;
                }
            }
        });

        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(title);
        box.add(Box.createHorizontalGlue());
        box.add(search);
        box.add(Box.createHorizontalStrut(3));

        add(gview, BorderLayout.CENTER);
        add(box, BorderLayout.SOUTH);
    }
}

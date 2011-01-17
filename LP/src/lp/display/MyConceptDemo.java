/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
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
public class MyConceptDemo  extends javax.swing.JPanel {

    public MyConceptDemo() {
        super(new BorderLayout());

                // create a new radial tree view
        final MyConceptDisplay gview = new MyConceptDisplay();
        Visualization vis = gview.getVisualization();

        gview.setBackground(this.getBackground());

        // create a search panel for the tree map
        SearchQueryBinding sq = new SearchQueryBinding(
             (Table)vis.getGroup(MyConceptDisplay.treeNodes), gview.m_label,
             (SearchTupleSet)vis.getGroup(Visualization.SEARCH_ITEMS));
        JSearchPanel search = sq.createSearchPanel();
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 11));
        search.setBackground(this.getBackground());

        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        title.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 16));

        gview.addControlListener(new ControlAdapter() {
            @Override
            public void itemEntered(VisualItem item, MouseEvent e) {
                if ( item.canGetString(gview.m_label) )
                    title.setText(item.getString(gview.m_label));
            }
            @Override
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
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

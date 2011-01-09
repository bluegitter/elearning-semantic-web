/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualItem;

/**
 *
 * @author william
 */
public class AllConceptPane extends javax.swing.JPanel {

    public AllConceptPane() {
        super(new FlowLayout());

        Color bg = new Color(240, 240, 240);

        final AllConceptDisplay d = new AllConceptDisplay();
        d.setBackground(bg);
        d.setForeground(Color.BLACK);
        setForeground(Color.BLACK);
        setBackground(bg);

        JSearchPanel search = new JSearchPanel(d.getVisualization(),
                AllConceptDisplay.treeNodes, Visualization.SEARCH_ITEMS, d.m_label, true, true);
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 0));
        search.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 11));
        search.setForeground(Color.BLACK);
        search.setBackground(bg);

        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        title.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 16));
        title.setForeground(Color.BLACK);

        d.addControlListener(new ControlAdapter() {

            @Override
            public void itemEntered(VisualItem item, MouseEvent e) {
                if (item.canGetString("name")) {
                    title.setText(item.getString("name"));
                }
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

        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(d, BorderLayout.CENTER);
        leftPane.add(box, BorderLayout.PAGE_END);

        JPanel conceptPane = new ConceptPane();
        add(leftPane);
        add(conceptPane);
    }
}

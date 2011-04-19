/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.map;

import algorithm.datastructure.LinkedEConcept;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import lp.LPApp;

/**
 *
 * @author shuaiguo
 */
public class E_Castle {

    public int x, y, type;
    private Rectangle rect;
    public LinkedEConcept concept;

    private int labelWidth, labelHeight, labelBase;

    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    public static Color[] colors = {Color.GREEN, Color.YELLOW, Color.RED};
    public static ImageIcon[] image = {new ImageIcon(MapBg.class.getResource("/lp/resources/easy.png")),
        new ImageIcon(MapBg.class.getResource("/lp/resources/middle.png")), new ImageIcon(MapBg.class.getResource("/lp/resources/hard.png"))};

    public E_Castle(int x, int y, LinkedEConcept c) {
        this.x = x;
        this.y = y;
        this.type = c.getConceptType();
        this.concept = c;

        rect = new Rectangle(x, y, getImage().getIconWidth(), getImage().getIconHeight());

        Graphics g = LPApp.getApplication().view.mainPanel.getGraphics();

        g.setColor(new Color(97, 97, 97));
        g.setFont(new Font("微软雅黑", Font.BOLD, 12));
        FontMetrics fm = g.getFontMetrics();
        labelBase = fm.getAscent();
        labelHeight = fm.getAscent() + fm.getDescent();
        labelWidth = fm.stringWidth(c.getConcept().getName());
    }

    public boolean dian(int ex, int ey) {
        return rect.contains(ex, ey);
    }

    private ImageIcon getImage() {
        return image[type];
    }

    public Color getColor() {
        return colors[type];
    }

    public void paint(Component c, Graphics2D g, int x, int y) {
        ImageIcon i = image[type];
        i.paintIcon(c, g, x, y);

        int sx = x + i.getIconWidth() / 2 - labelWidth / 2;
        int sy = y + i.getIconHeight() + 8;

        g.setColor(Color.WHITE);
        g.fillRect(sx - 4, sy - 4, labelWidth + 8, labelHeight + 4);
        g.setColor(Color.BLUE);
        g.drawRect(sx - 4, sy - 4, labelWidth + 8, labelHeight + 4);

        g.setFont(new Font("微软雅黑", Font.BOLD, 12));
        g.drawString(concept.getConcept().getName(), sx, sy + labelBase);
    }
}

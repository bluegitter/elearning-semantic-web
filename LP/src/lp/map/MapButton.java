package lp.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public abstract class MapButton {

    public Rectangle bound;
    private String text;
    public boolean hover;
    private MapBg parent;
    private ImageIcon outi, overi;
    private static ImageIcon OUT_ICON = new ImageIcon(MapBg.class.getResource("/lp/resources/btnout.png"));
    private static ImageIcon OVER_ICON = new ImageIcon(MapBg.class.getResource("/lp/resources/btnover.png"));

    public MapButton(MapBg parent, String text, int x, int y) {
        this(parent, text, x, y, OUT_ICON, OVER_ICON);
    }

    public MapButton(MapBg parent, String text, int x, int y, ImageIcon outi, ImageIcon overi) {
        this.text = text;
        this.parent = parent;
        hover = false;
        this.outi = outi;
        this.overi = overi;

        bound = new Rectangle(x, y, outi.getIconWidth(), outi.getIconHeight());
    }
    
    public MapButton(MapBg parent, String text, ImageIcon outi, ImageIcon overi) {
        this(parent, text, 0, 0, outi, overi);
    }
    
    public int getWidth() {
        return outi.getIconWidth();
    }
    
    public int getHeight() {
        return outi.getIconHeight();
    }

    public void paint(Graphics2D g) {
        if (hover) {
            overi.paintIcon(parent, g, bound.x, bound.y);
        } else {
            outi.paintIcon(parent, g, bound.x, bound.y);
        }

        if (!text.isEmpty()) {
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 14));
            g.drawString(text, bound.x + 18, bound.y + 22);
        }
    }

    public abstract void doAction();
}

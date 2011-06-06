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
    private int x, y;
    private MapBg parent;
    
    private static ImageIcon outi = new ImageIcon(MapBg.class.getResource("/lp/resources/btnout.png"));
    private static ImageIcon overi = new ImageIcon(MapBg.class.getResource("/lp/resources/btnover.png"));
    
    public MapButton(MapBg parent, String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.parent = parent;
        hover = false;
        
        bound = new Rectangle(x, y, outi.getIconWidth(), outi.getIconWidth());
    }
    
    public void paint(Graphics2D g) {
        if(hover) {
            overi.paintIcon(parent, g, x, y);
        } else {
            outi.paintIcon(parent, g, x, y);
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g.drawString(text, x + 18, y + 22);
    }
    
    public abstract void doAction();
}

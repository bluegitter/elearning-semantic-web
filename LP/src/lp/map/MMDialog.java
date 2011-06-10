package lp.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public abstract class MMDialog {
    public Rectangle bound, showRect, closeBtn;
    public boolean boundshow;
    public ImageIcon cicon = new ImageIcon(MapBg.class.getResource("/lp/resources/close.png"));
    
    public MMDialog() {
        bound = new Rectangle();
        showRect = new Rectangle();
        closeBtn = new Rectangle(cicon.getIconWidth(), cicon.getIconHeight());
        boundshow = false;
    }
    
    public abstract void paint(Graphics2D g, int vw, int vh);
    public abstract boolean mouseOn(int x, int y);
    public abstract boolean mouseClick(int x, int y);
}

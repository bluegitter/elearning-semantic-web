package lp.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author David
 */
public abstract class MMDialog {
    public Rectangle bound;
    
    public MMDialog() {
        bound = new Rectangle();
    }
    
    public abstract void paint(Graphics2D g, int vw, int vh);
    public abstract boolean mouseOn(int x, int y);
    public abstract boolean mouseClick(int x, int y);
}

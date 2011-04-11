/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.map;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author shuaiguo
 */
public class E_Castle {

    public int x, y, type;

    private Rectangle rect;

    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static ImageIcon[] image = {new ImageIcon(MapBg.class.getResource("/lp/resources/easy.png")),
        new ImageIcon(MapBg.class.getResource("/lp/resources/middle.png")), new ImageIcon(MapBg.class.getResource("/lp/resources/hard.png"))};

    public E_Castle(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        rect = new Rectangle(x, y, getImage().getIconWidth(), getImage().getIconHeight());
    }

    public boolean dian(int ex, int ey) {
        return rect.contains(ex, ey);
    }

    public ImageIcon getImage() {
        return image[type];
    }
}

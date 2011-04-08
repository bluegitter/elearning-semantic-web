package lp.map;

import javax.swing.ImageIcon;

public class MapBg {

    public int width, height;

    public ImageIcon bgImage;

    public MapBg(int width, int height) {
        this.width = width;
        this.height = height;

        bgImage = new ImageIcon(MapBg.class.getResource(null));
    }
}

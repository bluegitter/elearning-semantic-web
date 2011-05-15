package lp.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.LinkedHashMap;
import lp.LPApp;

/**
 *
 * @author shuaiguo
 */
public class MapInfoDialog {

    public Rectangle bound;
    private MapBg parent;
    private String title, info, action;

    private Stroke rstroke = new BasicStroke(2.0f);

    public MapInfoDialog(MapBg parent, String title, String info, String action) {
        bound = new Rectangle();
        this.title = title;
        this.info = info;
        this.action = action;
        this.parent = parent;

        initDialog();
    }

    private void initDialog() {
        Graphics g = LPApp.getApplication().view.mainPanel.getGraphics();
    }
}

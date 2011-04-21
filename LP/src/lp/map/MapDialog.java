/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedHashMap;
import lp.LPApp;
import ontology.EGoal;

/**
 *
 * @author shuaiguo
 */
public class MapDialog {

    public Rectangle bound;
    private HashMap<Rectangle, Object> actions = new HashMap<Rectangle, Object>();
    private LinkedHashMap<String, Object> options;
    private Object selected;
    private Rectangle ron, rselected;
    private int optionHeight, optionBase, abLength;
    private String title, abt;
    private MapBg parent;

    public MapDialog(MapBg parent, String title, LinkedHashMap<String, Object> options, String action) {
        bound = new Rectangle();
        this.options = options;
        this.title = title;
        this.parent = parent;
        abt = action;
        ron = rselected = null;

        initDialog();
    }

    private void initDialog() {
        Graphics g = LPApp.getApplication().view.mainPanel.getGraphics();

        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        optionHeight = fm.getHeight();
        optionBase = fm.getAscent();
        abLength = fm.stringWidth(abt);
        
        bound.setSize(400, (optionHeight + 20) * (options.size() + 2));
        
        int cy = 0;
        int opw = bound.width - 20;
        for (String ok : options.keySet()) {
            cy += 20 + optionHeight;
            actions.put(new Rectangle(10, cy, opw, 10 + optionHeight), options.get(ok));
        }

        cy += 20 + optionHeight;
        actions.put(new Rectangle((bound.width - abLength) / 2 - 10, cy, abLength + 20, 10 + optionHeight), "ok");
    }

    public void paint(Graphics2D g, int vw, int vh) {
        int cy = bound.y;
        bound.setLocation((vw - bound.width) / 2, (vh - bound.height) / 2);
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g.fill(bound);
        g.setColor(Color.BLACK);
        g.draw(bound);
        g.drawString(title, bound.x + 10, cy + 5 + optionBase);
        g.drawLine(bound.x, cy + 10 + optionHeight, bound.x + bound.width, cy + 10 + optionHeight);

        if(ron != null) {
            g.setColor(Color.YELLOW);
            g.fill3DRect(ron.x + bound.x, ron.y + bound.y, ron.width, ron.height, true);
        }

        if(rselected != null) {
            g.setColor(Color.MAGENTA);
            g.fill3DRect(rselected.x + bound.x, rselected.y + bound.y, rselected.width, rselected.height, true);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        int opx = bound.x + 20;
        for (String ok : options.keySet()) {
            cy += 20 + optionHeight;

            g.drawString(ok, opx, cy + 5 + optionBase);
        }

        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        cy += 20 + optionHeight;
        g.drawString(abt, bound.x + (bound.width - abLength) / 2, cy + 5 + optionBase);
    }

    public Object getSelected() {
        return selected;
    }

    private void doAction(Object action) {
        if(action instanceof String) {
            if(action.equals("ok")) {
                //todo: 设定选定
                parent.hideMapDialog();
            }
        }
    }

    public void mouseOn(int x, int y) {
        for(Rectangle rect : actions.keySet()) {
            if(rect.contains(x, y)) {
                if(ron != null && ron.equals(rselected))
                    ron = null;
                else
                    ron = rect;
                
                return;
            }
        }
        ron = null;
    }

    public boolean mouseClick(int x, int y) {
        for(Rectangle rect : actions.keySet()) {
            if(rect.contains(x, y)) {
                rselected = rect;
                selected = actions.get(rect);
                doAction(selected);
                return true;
            }
        }
        return false;
    }
}

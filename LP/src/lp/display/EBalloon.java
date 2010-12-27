package lp.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author David
 */
public class EBalloon implements Runnable {

    private float diameter;
    private float x, y;
    private Color color;
    private String label;
    private RadialGradientPaint brush;

    private float rd;

    public Thread thread;

    public EBalloon(float x, float y, float d, String label, Color color) {
        this.diameter = d;
        this.x = x;
        this.y = y;
        this.label = label;
        this.color = color;

        rd = diameter;
    }

    public void paint(Graphics2D g) {
        Point2D c = new Point2D.Float(x, y);
        float radius = rd / 2f;
        Point2D f = new Point2D.Float(x - rd / 4f, y - rd / 4f);
        float[] dist = {0.0f, 1f};
        Color[] colors = {Color.WHITE, color};
        brush = new RadialGradientPaint(c, radius, f, dist, colors, CycleMethod.NO_CYCLE);
        g.setPaint(brush);

        g.fill(new Ellipse2D.Float(x - rd /2f, y - rd / 2f, rd, rd));

        g.setColor(new Color(97, 97, 97));
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        int ch = fm.getAscent() + fm.getDescent();
        g.drawString(label, x - fm.stringWidth(label) / 2f, y - ch * 1.5f);
    }

    public void mouseon() {

    }

    public void mouseout() {
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

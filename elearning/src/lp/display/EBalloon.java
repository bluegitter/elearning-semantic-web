package lp.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author David
 */
public class EBalloon implements Runnable {

    public float diameter;
    private float x, y;
    private Color color, aColor, rColor;
    private String label;
    private RadialGradientPaint brush;
    private Ellipse2D.Float shape;
    public float rd;
    public Thread thread;
    public boolean ison, shown;

    public EBalloon(float x, float y, float d, String label, Color color) {
        this.diameter = d;
        this.x = x;
        this.y = y;
        this.label = label;
        this.color = color;
        this.aColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 222);
        this.rColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 111);

        shape = new Ellipse2D.Float(x - diameter / 2f, y - diameter / 2f, diameter, diameter);

        thread = null;
        ison = shown = false;
        rd = 0;
    }

    public void paint(Graphics2D g) {
        if (shown) {
            Point2D c = new Point2D.Float(x, y);
            float radius = rd / 2f;
            Point2D f = new Point2D.Float(x - rd / 4f, y - rd / 4f);
            float[] dist = {0.0f, 1f};
            Color[] colors = {Color.WHITE, ison ? aColor : color};
            brush = new RadialGradientPaint(c, radius, f, dist, colors, CycleMethod.NO_CYCLE);
            g.setPaint(brush);

            g.fill(new Ellipse2D.Float(x - rd / 2f, y - rd / 2f, rd, rd));

            g.setColor(new Color(97, 97, 97));
            g.setFont(new Font("微软雅黑", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            int ch = fm.getAscent() + fm.getDescent();
            g.drawString(label, x - fm.stringWidth(label) / 2f, y - ch * 1.2f);
        } else {
            g.setColor(rColor);
            g.fill(new Ellipse2D.Float(x - rd / 2f, y - rd / 2f, rd, rd));
        }
    }

    public boolean contains(Point p) {
        return shape.contains(p);
    }

    public void mouseon() {
        if(!shown)
            return;
        
        if (thread == null) {
            thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }
        this.ison = true;
    }

    public void mouseout() {
        if(!shown)
            return;
        
        if (thread == null) {
            thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }
        this.ison = false;
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();

        while (thread == me) {
            if(shown) {
                if (ison) {
                    rd += 6;
                    if (this.rd >= this.diameter + 20) {
                        break;
                    }
                } else {
                    rd -= 6;
                    if (this.rd <= this.diameter) {
                        break;
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }
        thread = null;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

/*
 * Copyright 2011 Notemate.org
 */
package lp.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class LPBar extends JPanel implements MouseListener, MouseMotionListener {

    private ImageIcon timage = new ImageIcon(LPBar.class.getResource("/lp/resources/tiaotiao.png"));
    private ImageIcon yimage = new ImageIcon(LPBar.class.getResource("/lp/resources/yuanyuan.png"));
    private double value;
    private boolean pressed;
    private int color;
    private static Color[] xc = {new Color(255, 180, 180), new Color(180, 255, 180), new Color(180, 180, 255)};
    private static Color[] yc = {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)};

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;

    public double getValue() {
        return value;
    }

    public LPBar(int color) {
        this.value = 50;
        this.color = color;
        this.pressed = false;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new GradientPaint(12, 0, xc[color], 212, 0, yc[color], true));

        g2d.fillRect(10, 12, (int) (value * 2 - 6), 18);
        timage.paintIcon(this, g, 0, 0);
        yimage.paintIcon(this, g, (int) (value * 2), 0);
    }

    private boolean onYuan(int x, int y) {
        return (Math.sqrt(Math.pow(12 + value * 2 - x, 2) + Math.pow(15 - y, 2)) <= 12);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(227, 32);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (onYuan(e.getX(), e.getY())) {
            this.pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.pressed) {
            double newval = (e.getX() - 12) / 2;
            if (newval < 0) {
                newval = 0;
            } else if (newval > 100) {
                newval = 100;
            }
            value = newval;

            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

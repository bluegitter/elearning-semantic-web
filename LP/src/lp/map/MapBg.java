package lp.map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class MapBg extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    public javax.swing.JPanel pp;
    public ImageIcon bgImage;
    private int w = 3000, h = 3000;
    private int cx, cy, vw, vh, bw, bh, ox, oy;
    private boolean init = false;

    private ArrayList<E_Castle> castle;

    public MapBg(javax.swing.JPanel p) {
        pp = p;
        bgImage = new ImageIcon(MapBg.class.getResource("/lp/resources/grass.png"));

        bw = bgImage.getIconWidth();
        bh = bgImage.getIconHeight();

        castle = new ArrayList<E_Castle>();

        addMouseListener(this);
        addMouseMotionListener(this);

        initCastle();
    }

    private void initCastle() {
        castle.add(new E_Castle(1400, 1200, E_Castle.HARD));
        castle.add(new E_Castle(1500, 1600, E_Castle.MEDIUM));
        castle.add(new E_Castle(1800, 1300, E_Castle.EASY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        vw = pp.getWidth();
        vh = pp.getHeight();

        if (!init) {
            init = true;
            cx = w / 2 - vw / 2;
            cy = h / 2 - vh / 2;
        }

        for (int i = 0; i <= w / bw; i++) {
            for (int j = 0; j <= h / bh; j++) {
                if (insideShow(i * bw, j * bh, bw, bh)) {
                    bgImage.paintIcon(this, g, i * bw - cx, j * bh - cy);
                }
            }
        }

        for(E_Castle c : castle) {
            c.getImage().paintIcon(this, g, c.x - cx, c.y - cy);
        }
    }

    private boolean insideShow(int x, int y, int w, int h) {
        int minx = Math.max(x, cx);
        int miny = Math.max(y, cy);
        int maxx = Math.min(x + w, cx + vw);
        int maxy = Math.min(y + h, cy + vh);

        return ! (minx > maxx || miny > maxy);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(pp.getWidth(), pp.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(E_Castle c : castle) {
            if(c.dian(e.getX() + cx, e.getY() + cy)) {
                javax.swing.JOptionPane.showMessageDialog(this, "被点中了。。。");
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cx -= e.getX() - ox;
        cy -= e.getY() - oy;
        if(cx < 0)
            cx = 0;
        else if (cx + vw > w)
            cx = w - vw;

        if(cy < 0)
            cy = 0;
        else if (cy + vh > h)
            cy = h - vh;

        ox = e.getX();
        oy = e.getY();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ;
    }
}

package lp.map;

import algorithm.datastructure.LinkedEConcept;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import lp.LPApp;
import ontology.EGoal;
import util.Constant;

public class MapBg extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    public javax.swing.JPanel pp;
    public ImageIcon bgImage;
    private int w = 3000, h = 3000;
    private int tw = 180, th = 180;
    private int cx, cy, vw, vh, bw, bh, ox, oy;
    private boolean init = false;
    private ArrayList<E_Castle> castle;
    private BufferedImage tMap;
    private HashMap<String, Point> cities;

    public MapBg(javax.swing.JPanel p) {
        pp = p;
        bgImage = new ImageIcon(MapBg.class.getResource("/lp/resources/grass.png"));

        bw = bgImage.getIconWidth();
        bh = bgImage.getIconHeight();

        castle = new ArrayList<E_Castle>();
        cities = new HashMap<String, Point>();

        tMap = new BufferedImage(tw, th, BufferedImage.TYPE_INT_ARGB);

        initCastle();
    }

    private void initCastle() {
        addMouseListener(this);
        addMouseMotionListener(this);

        loadLocation();
        ArrayList<EGoal> goals = LPApp.lpModel.getAllEGoals();

        Graphics2D ig = tMap.createGraphics();
        ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ig.setColor(new Color(35, 63, 216, 160));
        ig.fillRect(0, 0, tw, th);

        ig.setColor(Color.LIGHT_GRAY);
        ig.drawRect(0, 0, tw, th);

        for (EGoal goal : goals) {
            ArrayList<LinkedEConcept> list = LPApp.lpModel.getConceptsByELearnerGoal(LPApp.getApplication().user.learner, goal);
            for (LinkedEConcept le : list) {
                Point p = cities.get(le.getConcept().getCid());
                E_Castle ecastle = new E_Castle(p.x, p.y, le);
                castle.add(ecastle);

                ig.setColor(ecastle.getColor());
                ig.fillOval(p.x * tw / w, p.y * th / h, 5, 5);
            }
        }
    }

    private void loadLocation() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(Constant.CITY_DATA));
            String line;

            while ((line = in.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length > 2) {
                    cities.put(data[0], new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }

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

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (E_Castle c : castle) {
            c.paint(this, g2, c.x - cx, c.y - cy);
        }

        g2.drawImage(tMap, vw - tw - 5, 5, null);
        g2.setColor(Color.WHITE);
        g2.drawRect(vw - tw - 5 + cx * tw / w, 5 + cy * th / h, vw * tw / w, vh * th / h);
    }

    private boolean insideShow(int x, int y, int w, int h) {
        int minx = Math.max(x, cx);
        int miny = Math.max(y, cy);
        int maxx = Math.min(x + w, cx + vw);
        int maxy = Math.min(y + h, cy + vh);

        return !(minx > maxx || miny > maxy);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(pp.getWidth(), pp.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (E_Castle c : castle) {
            if (c.dian(e.getX() + cx, e.getY() + cy)) {
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
        if (cx < 0) {
            cx = 0;
        } else if (cx + vw > w) {
            cx = w - vw;
        }

        if (cy < 0) {
            cy = 0;
        } else if (cy + vh > h) {
            cy = h - vh;
        }

        ox = e.getX();
        oy = e.getY();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ;
    }
}

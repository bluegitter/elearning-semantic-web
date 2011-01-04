package lp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import lp.display.EBalloon;
import lp.display.EClass;
import ontology.EConcept;

/**
 *
 * @author Shuaiguo Wang
 */
public class RecommendPane extends javax.swing.JPanel implements MouseListener, MouseMotionListener, Runnable {

    private ArrayList<EBalloon> balloons;
    private EBalloon on;
    private int showTime;
    public Thread thread;

    public RecommendPane() {
        on = null;
        thread = null;
        showTime = 0;

        this.balloons = new ArrayList<EBalloon>();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (EBalloon b : balloons) {
            b.paint(g2d);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                javax.swing.JFrame app = new javax.swing.JFrame();
                RecommendPane p = new RecommendPane();
                app.setContentPane(p);
                p.setPreferredSize(new java.awt.Dimension(600, 400));
                app.pack();
                app.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                app.setVisible(true);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ;
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
        ;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        EBalloon balloon = null;
        for (EBalloon b : balloons) {
            if (b.contains(e.getPoint())) {
                balloon = b;
            }
        }
        if (on != balloon) {
            if (on != null) {
                on.mouseout();
                on = null;
            }
            if (balloon != null) {
                on = balloon;
                on.mouseon();
            }
            rpstart();
        }
    }

    public void rpstart() {
        if (thread == null) {
            thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.setName("Balloon Demo");
            thread.start();
        }
        this.showTime = 0;
    }

    public void reRecommend(double[] r)  {
        this.balloons.clear();
        HashMap<String, EClass> map = new HashMap<String, EClass>();
        for (int i = 1; i <= 3; i++) {
            ArrayList<EConcept> l = LPApp.lpModel.getRecommendEConcepts(LPApp.getApplication().user.learner, i);
            for(EConcept c : l) {
                if(map.containsKey(c.getCid())) {
                    EClass ec = map.get(c.getCid());
                    ec.rank += r[i - 1];
                } else {
                    EClass ec = new EClass(c);
                    map.put(c.getCid(), ec);
                    ec.rank += r[i - 1];
                }
            }
        }
        ArrayList<EClass> list = new ArrayList(map.size());
        for(EClass tec: map.values()) {
            list.add(tec);
        }
        java.util.Collections.sort(list);

        java.awt.Color[] colors = {java.awt.Color.RED, java.awt.Color.BLUE, java.awt.Color.GREEN};
        float[] x = {200, 400, 600, 800, 200, 400}, y= {200, 200, 200, 200, 400, 400};
        int len = list.size() > 6 ? 6 : list.size();
        System.out.println(len);
        for(int i = 0; i < len; i++) {
            EClass nec = list.get(i);
            System.out.println(nec.toString() + " " + nec.rank);

            this.balloons.add(new EBalloon(x[i], y[i], 150, nec.toString(), colors[i % 3]));
        }
        this.repaint();
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();

        while (thread == me && this.showTime <= 6) {
            repaint();
            showTime++;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }

        thread = null;
    }
}

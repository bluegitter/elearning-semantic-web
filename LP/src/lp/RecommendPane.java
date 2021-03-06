package lp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import lp.display.EBalloon;
import lp.display.EClass;
import lp.eresource.ResourceTablePane;
import ontology.EConcept;
import util.LogConstant;

/**
 *
 * @author Shuaiguo Wang
 */
public class RecommendPane extends javax.swing.JPanel implements MouseListener, MouseMotionListener, Runnable {

    private ArrayList<EBalloon> balloons;
    private EBalloon on, clicked;
    private int showTime;
    private boolean showing;
    public Thread thread;
    public ResourceTablePane resPane;

    public RecommendPane() {
        on = clicked = null;
        thread = null;
        showTime = 0;

        resPane = new ResourceTablePane();

        this.balloons = new ArrayList<EBalloon>();

        this.setDoubleBuffered(true);

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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (on != null && on != clicked) {
            if (clicked != null) {
                clicked.isClicked = false;
            }
            clicked = on;
            on.isClicked = true;

            LPApp.lpLogs.writeLog(  "泡泡推荐：" + on.ec.getCid(), LogConstant.STATUS104);
            resPane.updateResources(LPApp.lpModel.getEResourcesByEConcept(on.ec));

            repaint();
        }
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
            rpstart(6);
        }
    }

    public void rpstart(int st) {
        if (thread == null) {
            thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.setName("Balloon Demo");
            thread.start();
        }
        this.showTime = st;
    }

    public void reRecommend(double[] r) {
        showing = true;
        this.balloons.clear();
        on = clicked = null;
        HashMap<String, EClass> map = new HashMap<String, EClass>();
        Color[] ca = {new Color(220, 36, 36), new Color(141, 255, 48), new Color(127, 218, 255)};
        for (int i = 1; i <= 3; i++) {
            ArrayList<EConcept> l = jena.ELearnerReasoner.getRecommendEConcepts(LPApp.lpModel.getOntModel(), LPApp.getApplication().user.learner, i);
            for (EConcept c : l) {
                if (map.containsKey(c.getCid())) {
                    EClass ec = map.get(c.getCid());
                    ec.rank += r[i - 1];
                    ec.r[i - 1] = (float) r[i - 1];
                } else {
                    EClass ec = new EClass(c);
                    map.put(c.getCid(), ec);
                    ec.rank += r[i - 1];
                    ec.r[i - 1] = (float) r[i - 1];
                }
            }
        }
        ArrayList<EClass> list = new ArrayList(map.size());
        for (EClass tec : map.values()) {
            list.add(tec);
        }
        java.util.Collections.sort(list);

        float[] x = {422, 255, 573, 266, 572, 477}, y = {265, 155, 154, 356, 336, 457}, d = {190, 150, 150, 150, 110, 90};
        int len = list.size() > 6 ? 6 : list.size();
        for (int i = 0; i < len; i++) {
            EClass nec = list.get(i);

            EBalloon newb = new EBalloon(x[i], y[i], d[i], (EConcept) nec.object, ca[nec.getColorIndex()]);
            this.balloons.add(newb);
        }
        rpstart(24);
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();

        while (thread == me && this.showTime >= 0) {
            if (showing) {
                for (EBalloon b : balloons) {
                    if (!b.shown) {
                        b.rd += b.diameter / 20f;
                        if (b.rd >= b.diameter) {
                            b.shown = true;
                        }
                    }
                }
            }

            repaint();
            showTime--;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }

        showing = false;
        thread = null;
    }
//       public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                javax.swing.JFrame app = new javax.swing.JFrame();
//                RecommendPane p = new RecommendPane();
//                app.setContentPane(p);
//                p.setPreferredSize(new java.awt.Dimension(600, 400));
//                app.pack();
//                app.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                app.setVisible(true);
//            }
//        });
//    }
}

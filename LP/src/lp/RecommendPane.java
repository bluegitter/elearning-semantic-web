package lp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import lp.display.EBalloon;

/**
 *
 * @author Shuaiguo Wang
 */
public class RecommendPane extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    ArrayList<EBalloon> balloons;

    public RecommendPane() {
        this.balloons = new ArrayList<EBalloon>();
        balloons.add(new EBalloon(200, 200, 150, "软件工程", java.awt.Color.RED));
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
        ;
    }
}

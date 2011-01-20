package algorithm.random;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import lp.display.EBalloon;
import ontology.EConcept;

public class MyPane extends javax.swing.JPanel implements MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 185523662421054934L;
    private ArrayList<EBalloon> balloons;

    public static void main(String[] args) {
        javax.swing.JFrame app = new javax.swing.JFrame();
        MyPane p = new MyPane();
        p.setVisible(true);
        app.setContentPane(p);
        p.setPreferredSize(new java.awt.Dimension(1000, 600));
        app.setVisible(true);
        app.pack();
        app.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public MyPane() {
        this.balloons = new ArrayList<EBalloon>();
        float[] x = {200, 400, 600, 800, 200, 400}, y = {200, 200, 200, 200, 400, 400};
        for (int i = 0; i < 6; i++) {
            balloons.add(new EBalloon(x[i], y[i], 150, new EConcept("temp"), Color.BLACK));
        }
        addMouseListener(this);
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
        // TODO Auto-generated method stub
        System.out.println("click");
        double x = this.getSize().getWidth();
        double y = this.getSize().getHeight();
        ArrayList<Cell> cells = RandomEBalloon.getRandomBallons(balloons, (float) x, (float) y);
        for (int i = 0; i < 6; i++) {
            float diameter = balloons.get(i).getDiameter();
            Couple center = RandomEBalloon.getCenter(cells.get(i), diameter);
            System.out.println(center);
            balloons.set(i, new EBalloon(center.x, center.y, diameter, new EConcept("temp"), Color.RED));
        }
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}

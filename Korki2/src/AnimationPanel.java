package kolos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    double x = 60;
    double y = 60;
    int size = 60;
    int width;
    int height;
    int fps = 1000 / 60;
    int delta = 5;
    boolean isMoving = false;
    private Thread animThreat; //w¹tek

    public enum Kierunek {
        GORA,
        DOL,
        LEWO,
        PRAWO
    }

    Kierunek kierunek1 = Kierunek.LEWO;

    int kierunek2 = 0; //0 - lewo, 1 - dol, 2 - gora, 3 - prawo

    //rysowanie kwadratu
    public AnimationPanel() {
        setBackground(Color.WHITE);
        setDoubleBuffered(true);//zwiêksza p³ynnoœæ animacji
    }

    public void initGUI() {

        animThreat = new Thread(this.rysowanie);
        animThreat.start();

        this.width = this.getWidth();
        this.height = this.getHeight();

        this.x = (this.width / 2) - this.size / 2;
        this.y = (this.height / 2) - this.size / 2;
    }

    public void paint(Graphics g) {
        super.paint(g);




        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLUE);
        g2.draw(new Rectangle2D.Double(x, y, size, size));

    }

    public void movement() {

        if (isMoving) {
            /*
            switch (kierunek2) {
                case 0:
                    x -= delta;
                    break;
                case 1:
                    y += delta;
                    break;
                case 2:
                    y -= delta;
                    break;
                case 3:
                    x += delta;
                    break;
            }
            */

            switch (kierunek1) {
                case DOL -> {
                    if ((y + delta) + this.size > this.height) {
                        isMoving = false;
                        break;
                    }
                    y += delta;
                }
                case GORA -> {
                    if ((y - delta) < 0) {
                        isMoving = false;
                        break;
                    }
                    y -= delta;
                }
                case LEWO -> {
                    if ((x - delta) < 0) {
                        isMoving = false;
                        break;
                    }
                    x -= delta;
                }
                case PRAWO -> {
                    if ((x + delta) + this.size > this.width) {
                        isMoving = false;
                        break;
                    }
                    x += delta;
                }
            }

        }

    }

    public Runnable rysowanie = () -> {
        while (true) {
            movement();
            this.repaint();

            try {
                Thread.sleep(this.fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void switchAnimationState1() {
        this.isMoving = true;
        this.kierunek1 = Kierunek.GORA;
    }

    public void switchAnimationState2() {
        this.isMoving = true;
        this.kierunek1 = Kierunek.DOL;
    }

    public void switchAnimationState3() {
        this.isMoving = true;
        this.kierunek1 = Kierunek.PRAWO;
    }

    public void switchAnimationState4() {
        this.isMoving = true;
        this.kierunek1 = Kierunek.LEWO;
    }
}

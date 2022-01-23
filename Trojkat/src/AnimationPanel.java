import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {

    private Trojkat trojkat = null;

    private Dimension rozmiary;

    public AnimationPanel() {
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
    }

    public record Trojkat(double a, double b, double c) {}

    public void paint(Graphics g) {
        super.paint(g);
        if (this.trojkat != null) {
            int marginesX = (int) (this.getWidth() * 0.1);
            int marginesY = (int) (this.getHeight() * 0.1);

            int w = (int) (this.getWidth() * 0.8), h = (int) (this.getHeight() * 0.8);

            var ab = new Point(marginesX, h + marginesY);
            var ac = new Point(marginesX, marginesY + (int) (h * (1 - this.trojkat.a)));
            var bc = new Point(marginesX + (int) (w * this.trojkat.b), h + marginesY);

            Graphics2D g2 = (Graphics2D) g;

            g2.drawLine(ab.x, ab.y, ac.x, ac.y);
            g2.drawLine(ab.x, ab.y, bc.x, bc.y);
            g2.drawLine(ac.x, ac.y, bc.x, bc.y);
        }
    }


    public void narysujTrojkat(Trojkat t) {
        this.trojkat = this.normalize(t);
        this.repaint();
    }

    private Trojkat normalize(Trojkat t) {
        double scaleA = t.a;
        double scaleB = t.b;

        if (scaleA > 1 && scaleB > 1) {
            double aN, bN, cN;
            if (scaleA > scaleB) {
                aN = t.a / scaleA;
                bN = t.b / scaleA;
                cN = t.c / scaleA;
            } else {
                aN = t.a / scaleB;
                bN = t.b / scaleB;
                cN = t.c / scaleB;
            }

            return new Trojkat(aN, bN, cN);
        }

        return t;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Warcaby extends JPanel {
    private static final int POLA = 8;
    public static void drawWarcaby(Graphics2D g, double x, double y, double size) {
        final double step = size / 8f;
        boolean wr = false;
        for (int i = 0; i < Warcaby.POLA; i++) {
            for (int j = 0; j < Warcaby.POLA; j++) {
                if (wr) g.setColor(Color.WHITE);
                else g.setColor(Color.DARK_GRAY);
                g.fill(new Rectangle2D.Double(x + (step * j), y + (step * i), step, step));
                wr = !wr;
            }
            wr = !wr;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        Warcaby.drawWarcaby(g2, 50d, 30d, 375d);
    }
}

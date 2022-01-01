import javax.swing.*;
import java.awt.*;

public class Warcaby4 extends JPanel {
    private static final double SIZE = 175d;
    private static final double BORDER = 25d;
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Warcaby.drawWarcaby(
                        g2,
                        50 + (i * (Warcaby4.BORDER + Warcaby4.SIZE)),
                        30 + (j * (Warcaby4.BORDER + Warcaby4.SIZE)),
                        Warcaby4.SIZE);
            }
        }
    }
}

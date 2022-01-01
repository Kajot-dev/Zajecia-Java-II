import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class PlanszaGry extends JPanel {
	public void paint(Graphics g) {
		super.paint(g);
		
		setBackground(Color.WHITE);
		
		Graphics2D g2 = (Graphics2D) g;
		
		Stroke defaultStroke = g2.getStroke();
		
		// linie planszy
		float dash[] = {10.0f};
		Stroke lineStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 15.0f);
		g2.setStroke(lineStroke);
		g2.setColor(Color.GRAY);
		g2.draw(new Line2D.Double(200, 100, 200, 400));
		g2.draw(new Line2D.Double(300, 100, 300, 400));
		g2.draw(new Line2D.Double(100, 200, 400, 200));
		g2.draw(new Line2D.Double(100, 300, 400, 300));
		
		// kó³ka i kwadraty
		int w = 60;
		int h = 60;
		int m = 20;
		
//		g2.setPaint(Color.YELLOW);
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource("pattern2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		g2.setPaint(new TexturePaint(img, new Rectangle2D.Double(0, 0, 20, 20)));
		g2.fill(new Ellipse2D.Double(100 + m, 100 + m, w, h));
		g2.fill(new Ellipse2D.Double(100 + m, 200 + m, w, h));
		g2.fill(new Ellipse2D.Double(300 + m, 300 + m, w, h));
		
		//g2.setPaint(Color.BLUE);
		g2.setPaint(new GradientPaint(200 + m, 100 + m, Color.BLUE, 200 + m + 100, 100 + m + 100, Color.WHITE));
		g2.fill(new Rectangle2D.Double(200 + m, 100 + m, w, h));
		g2.setPaint(new GradientPaint(100 + m, 300 + m, Color.BLUE, 100 + m + 100, 300 + m + 100, Color.WHITE));
		g2.fill(new Rectangle2D.Double(100 + m, 300 + m, w, h));
		g2.setPaint(new GradientPaint(200 + m, 300 + m, Color.BLUE, 200 + m + 100, 300 + m + 100, Color.WHITE));
		g2.fill(new Rectangle2D.Double(200 + m, 300 + m, w, h));
		
		
		// obrazki
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(this.getClass().getResource("smiley.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		g.drawImage(img2, 200 + m, 200 + m, null);
		
		
		// linia
		g2.setStroke(new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		g2.setColor(Color.RED);
		g2.draw(new Line2D.Double(100, 100, 400, 400));
	}
}

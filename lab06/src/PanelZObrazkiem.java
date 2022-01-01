import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelZObrazkiem extends JPanel {
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		setBackground(Color.BLACK);

		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource("house2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int x = 60;
		int y = 30;
		int w = 375;
		int h = 375;

		// obrazek
		g.drawImage(img, x, y, null);

		// skalowanie przez koordynaty
//		 g.drawImage(img,
//				 x, y, x + w, y + h, /* dst rectangle */
//				 x, y, w/2, h/2, /* src area of image */
//				 null);

		// skalowanie przez transformacjê
//		g2.translate(x, y);
//		g2.drawImage(img, AffineTransform.getScaleInstance(0.7, 0.7), null);
//		g2.drawImage(img, AffineTransform.getScaleInstance(1.5, 1.5), null);

		// skalowanie przez transformacjê z interoplacj¹
//		AffineTransform at = AffineTransform.getScaleInstance(1.5, 1.5);
//		AffineTransformOp aop = new AffineTransformOp(at,
//				AffineTransformOp.TYPE_BICUBIC);
//		g2.drawImage(img, aop, 0, 0);

		// rotacja
//		g2.translate(x, y);
//		AffineTransform affineTransform = new AffineTransform();
//		affineTransform.rotate(Math.toRadians(90), w / 2, h / 2);
//		g2.drawImage(img, affineTransform, this);

		// wyostrzanie
//		final float[] SHARPEN3x3 = { // sharpening filter kernel
//				0.f, -1.f, 0.f,
//				-1.f, 5.f, -1.f,
//				0.f, -1.f, 0.f
//		};
//		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, SHARPEN3x3),
//				ConvolveOp.EDGE_NO_OP, null);
//		g2.drawImage(img, cop, x, y);

		// rozmycie
//		 final float[] BLUR3x3 = { // low-pass filter kernel
//				 0.1f, 0.1f, 0.1f,
//				 0.1f, 0.2f, 0.1f,
//				 0.1f, 0.1f, 0.1f
//		 };
//		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, BLUR3x3),
//				ConvolveOp.EDGE_NO_OP, null);
//		g2.drawImage(img, cop, x, y);

		// modyfikacja (skalowanie + przesuniêcie) pojedynczych pikseli na
		// pojedynczych kana³ach
		// ka¿dy kana³ na pikselu razy 1.2 i plus 80
//		BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//		Graphics big = bi2.getGraphics();
//		big.drawImage(img, 0, 0, null);
//		RescaleOp rop = new RescaleOp(1.2f, 80.0f, null);
//		g2.drawImage(bi2, rop, x, y);

		// modyfikacja (modyfikacja) pojedynczych pikseli na pojedynczych
		// kana³ach
		// odwrócenie wartoœci kana³ów pikseli
//		byte lut[] = new byte[256];
//		for (int j = 0; j < 256; j++) {
//			lut[j] = (byte) (256 - j);
//		}
//		ByteLookupTable blut = new ByteLookupTable(0, lut);
//		LookupOp lop = new LookupOp(blut, null);
//		g2.drawImage(img, lop, x, y);

		// przezroczystoœæ 50%
//		BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//		Graphics big = bi2.getGraphics();
//		big.drawImage(img, 0, 0, null);
//		float[] scales = { 1f, 1f, 1f, 0.5f };
//		float[] offsets = new float[4];
//		RescaleOp rop = new RescaleOp(scales, offsets, null);
//		g2.drawImage(bi2, rop, x, y);

	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Wykres extends JPanel {
	public Wykres() {
		setLayout(new BorderLayout());
		
		JFreeChart chart = generateChart();
		
		ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel, BorderLayout.CENTER);
	}
	
	private JFreeChart generateChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(3, "Iloœæ", "Kwadraty");
		dataset.addValue(4, "Iloœæ", "Kó³ka");
		dataset.addValue(15, "Czas gry", "Kwadraty");
		dataset.addValue(12, "Czas gry", "Kó³ka");
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Wykres z u¿yciem JFreeChart", 
				"Wyniki gry", 
				"Iloœæ", 
				dataset,
				PlotOrientation.HORIZONTAL,
				true,
				true, 
				false);
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.getTitle().setPaint(Color.DARK_GRAY);
		
		
		CategoryPlot p = chart.getCategoryPlot();
		p.setBackgroundPaint(Color.WHITE);
		p.setRangeGridlinePaint(Color.BLUE);
		
		NumberAxis rangeAxis = (NumberAxis) p.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0, 16);
		
        BarRenderer renderer = (BarRenderer) p.getRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
		renderer.setSeriesPaint(1, Color.RED);
		return chart;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		try {
			BufferedImage img = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("smiley.png")));
			g.drawImage(img, 350, 70, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

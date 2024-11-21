import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class SpeedupEvolutionChart extends JFrame {

	public SpeedupEvolutionChart(String title) {
		super(title);

		XYSeriesCollection dataset = createSpeedupDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Matrix Multiplication Speedup Evolution",
				"Matrix Size (N x N)",
				"Speedup (x)",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);

		XYPlot plot = chart.getXYPlot();
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);
		plot.setBackgroundPaint(new Color(230, 230, 230));

		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setAutoRangeIncludesZero(true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1200, 800));
		setContentPane(chartPanel);
	}

	private XYSeriesCollection createSpeedupDataset() {
		XYSeries parallelSeries = new XYSeries("Parallel Speedup");
		XYSeries vectorizedSeries = new XYSeries("Vectorized Speedup");

		int[] sizes = {10, 50, 100, 200, 500, 1000};
		double[] parallelSpeedup = {0.11, 0.91, 3.04, 8.69, 10.94, 38.43};
		double[] vectorizedSpeedup = {1.00, 1.01, 1.01, 1.28, 1.81, 3.04};

		for (int i = 0; i < sizes.length; i++) {
			parallelSeries.add(sizes[i], parallelSpeedup[i]);
			vectorizedSeries.add(sizes[i], vectorizedSpeedup[i]);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(parallelSeries);
		dataset.addSeries(vectorizedSeries);

		return dataset;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpeedupEvolutionChart chart = new SpeedupEvolutionChart("Matrix Multiplication Speedup Evolution");
			chart.setSize(1200, 800);
			chart.setLocationRelativeTo(null);
			chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			chart.setVisible(true);
		});
	}
}

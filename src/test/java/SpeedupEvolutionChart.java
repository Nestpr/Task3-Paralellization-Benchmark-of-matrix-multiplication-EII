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

		// Create dataset
		XYSeriesCollection dataset = createSpeedupDataset();

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Matrix Multiplication Speedup Evolution", // Chart title
				"Matrix Size (N x N)",                   // X-axis label
				"Speedup (x)",                           // Y-axis label
				dataset,                                 // Dataset
				PlotOrientation.VERTICAL,
				true,                                    // Include legend
				true,
				false
		);

		// Customize the plot
		XYPlot plot = chart.getXYPlot();
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);
		plot.setBackgroundPaint(new Color(230, 230, 230));

		// Customize the axes
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setAutoRangeIncludesZero(true);

		// Set chart panel
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1200, 800));
		setContentPane(chartPanel);
	}

	private XYSeriesCollection createSpeedupDataset() {
		// Create datasets for Parallel and Vectorized speedups
		XYSeries parallelSeries = new XYSeries("Parallel Speedup");
		XYSeries vectorizedSeries = new XYSeries("Vectorized Speedup");

		// Data (from provided results)
		int[] sizes = {10, 50, 100, 200, 500, 1000};
		double[] parallelSpeedup = {0.11, 0.91, 3.04, 8.69, 10.94, 38.43};
		double[] vectorizedSpeedup = {1.00, 1.01, 1.01, 1.28, 1.81, 3.04};

		for (int i = 0; i < sizes.length; i++) {
			parallelSeries.add(sizes[i], parallelSpeedup[i]);
			vectorizedSeries.add(sizes[i], vectorizedSpeedup[i]);
		}

		// Combine datasets
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

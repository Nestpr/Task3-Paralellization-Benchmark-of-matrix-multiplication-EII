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

public class MatrixMultiplicationMemoryChart extends JFrame {

	public MatrixMultiplicationMemoryChart(String title) {
		super(title);

		// Create dataset
		XYSeriesCollection dataset = createMemoryUsageDataset();

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Matrix Multiplication: Memory Usage", // Chart title
				"Matrix Size (N x N)",               // X-axis label
				"Memory Usage (MB)",                 // Y-axis label
				dataset,                             // Dataset
				PlotOrientation.VERTICAL,
				true,                                // Include legend
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

	private XYSeriesCollection createMemoryUsageDataset() {
		// Create datasets for Basic, Parallel, and Vectorized memory usage
		XYSeries basicSeries = new XYSeries("Basic Memory Usage");
		XYSeries parallelSeries = new XYSeries("Parallel Memory Usage");
		XYSeries vectorizedSeries = new XYSeries("Vectorized Memory Usage");

		// Data (replace these with actual values)
		int[] sizes = {10, 50, 100, 200, 500, 1000};
		double[] basicMemory = {0.04, 0.04, 0.09, 0.34, 2.11, 8.12};
		double[] parallelMemory = {0.06, 0.04, 0.24, 0.49, 2.16, 8.39};
		double[] vectorizedMemory = {0.04, 0.04, 0.09, 0.34, 2.03, 8.21};

		for (int i = 0; i < sizes.length; i++) {
			basicSeries.add(sizes[i], basicMemory[i]);
			parallelSeries.add(sizes[i], parallelMemory[i]);
			vectorizedSeries.add(sizes[i], vectorizedMemory[i]);
		}

		// Combine datasets
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(basicSeries);
		dataset.addSeries(parallelSeries);
		dataset.addSeries(vectorizedSeries);

		return dataset;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MatrixMultiplicationMemoryChart chart = new MatrixMultiplicationMemoryChart("Matrix Multiplication Memory Usage");
			chart.setSize(1200, 800);
			chart.setLocationRelativeTo(null);
			chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			chart.setVisible(true);
		});
	}
}
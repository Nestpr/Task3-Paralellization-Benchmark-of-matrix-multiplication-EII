import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class MatrixMultiplicationChart extends JFrame {

	public MatrixMultiplicationChart(String title) {
		super(title);

		// Create dataset
		XYSeriesCollection dataset = createDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Matrix Multiplication Execution Times", // Chart title
				"Matrix Size (N x N)",                  // X-axis label
				"Execution Time (ms)",                 // Y-axis label
				dataset,                               // Dataset
				PlotOrientation.VERTICAL,
				true,                                  // Include legend
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
		domainAxis.setAutoRangeIncludesZero(false);

		LogAxis rangeAxis = new LogAxis("Execution Time (ms) [Log Scale]");
		rangeAxis.setBase(10);
		rangeAxis.setSmallestValue(0.001);
		rangeAxis.setRange(0.001, 10000); // Adjust range to fit all data points
		plot.setRangeAxis(rangeAxis);

		// Set chart panel
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1200, 800));
		setContentPane(chartPanel);
	}

	private XYSeriesCollection createDataset() {
		// Create datasets for Basic, Parallel, and Vectorized
		XYSeries basicSeries = new XYSeries("Basic");
		XYSeries parallelSeries = new XYSeries("Parallel");
		XYSeries vectorizedSeries = new XYSeries("Vectorized");

		// Add data (replace these values with your JMH results)
		int[] sizes = {10, 50, 100, 200, 500, 1000};
		double[] basicTimes = {0.002, 0.195, 1.409, 15.285, 417.164, 5156.395};
		double[] parallelTimes = {0.021, 0.196, 0.465, 1.805, 33.502, 146.230};
		double[] vectorizedTimes = {0.002, 0.162, 1.338, 13.490, 201.349, 1889.233};

		for (int i = 0; i < sizes.length; i++) {
			basicSeries.add(sizes[i], basicTimes[i]);
			parallelSeries.add(sizes[i], parallelTimes[i]);
			vectorizedSeries.add(sizes[i], vectorizedTimes[i]);
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
			MatrixMultiplicationChart chart = new MatrixMultiplicationChart("Matrix Multiplication Performance");
			chart.setSize(1200, 800);
			chart.setLocationRelativeTo(null);
			chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			chart.setVisible(true);
		});
	}
}

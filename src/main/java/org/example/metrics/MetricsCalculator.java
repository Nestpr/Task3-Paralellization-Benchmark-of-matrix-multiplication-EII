package org.example.metrics;

public class MetricsCalculator {

	public static ExecutionMetrics calculateExecutionMetrics(double sequentialTime, double parallelTime, double vectorizedTime) {
		return new ExecutionMetrics(sequentialTime, parallelTime, vectorizedTime);
	}
}


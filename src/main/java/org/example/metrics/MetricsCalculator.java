package org.example.metrics;

public class MetricsCalculator {

	public static ExecutionMetrics calculateExecutionMetrics(double sequentialTime, double parallelTime, double vectorizedTime) {
		return new ExecutionMetrics(sequentialTime, parallelTime, vectorizedTime);
	}

	public static void printMetrics(ExecutionMetrics metrics) {
		System.out.println("Execution Metrics:");
		System.out.println("Sequential Time: " + metrics.getSequentialTime() + " ms");
		System.out.println("Parallel Time: " + metrics.getParallelTime() + " ms");
		System.out.println("Vectorized Time: " + metrics.getVectorizedTime() + " ms");
		System.out.println("Parallel Speedup: " + metrics.getSpeedupParallel());
		System.out.println("Vectorized Speedup: " + metrics.getSpeedupVectorized());
	}
}


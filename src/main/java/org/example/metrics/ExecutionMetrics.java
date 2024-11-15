package org.example.metrics;

public class ExecutionMetrics {
	private final double sequentialTime;
	private final double parallelTime;
	private final double vectorizedTime;

	public ExecutionMetrics(double sequentialTime, double parallelTime, double vectorizedTime) {
		this.sequentialTime = sequentialTime;
		this.parallelTime = parallelTime;
		this.vectorizedTime = vectorizedTime;
	}

	// Getter for sequentialTime
	public double getSequentialTime() {
		return sequentialTime;
	}

	// Getter for parallelTime
	public double getParallelTime() {
		return parallelTime;
	}

	// Getter for vectorizedTime
	public double getVectorizedTime() {
		return vectorizedTime;
	}

	// Calculates speedup for parallel execution
	public double getSpeedupParallel() {
		return sequentialTime / parallelTime;
	}

	// Calculates speedup for vectorized execution
	public double getSpeedupVectorized() {
		return sequentialTime / vectorizedTime;
	}
}
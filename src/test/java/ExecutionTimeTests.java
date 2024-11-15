import org.example.matrix.BasicMatrixMultiplication;
import org.example.matrix.Matrix;
import org.example.matrix.ParallelMatrixMultiplication;
import org.example.matrix.VectorizedMatrixMultiplication;
import org.example.metrics.ExecutionMetrics;
import org.example.metrics.MetricsCalculator;
import org.junit.jupiter.api.Test;

public class ExecutionTimeTests {

	@Test
	public void testMatrixMultiplicationPerformance() {
		int[] sizes = {10, 50, 100, 200, 500, 1000}; // Matrix sizes to test

		for (int size : sizes) {
			System.out.println("Testing matrix multiplication for size: " + size + " x " + size);

			// Generate matrices
			double[][] matrixA = Matrix.generateMatrix(size, size);
			double[][] matrixB = Matrix.generateMatrix(size, size);

			Runtime runtime = Runtime.getRuntime();

			// Measure Basic (Sequential) Multiplication
			runtime.gc(); // Perform garbage collection to get accurate memory usage
			long memoryBeforeSequential = runtime.totalMemory() - runtime.freeMemory();
			long startSequential = System.nanoTime();
			double[][] resultSequential = BasicMatrixMultiplication.multiply(matrixA, matrixB);
			long endSequential = System.nanoTime();
			long memoryAfterSequential = runtime.totalMemory() - runtime.freeMemory();
			double sequentialTime = (endSequential - startSequential) / 1e6;

			// Measure Parallel Multiplication
			runtime.gc();
			long memoryBeforeParallel = runtime.totalMemory() - runtime.freeMemory();
			long startParallel = System.nanoTime();
			double[][] resultParallel = ParallelMatrixMultiplication.multiply(matrixA, matrixB);
			long endParallel = System.nanoTime();
			long memoryAfterParallel = runtime.totalMemory() - runtime.freeMemory();
			double parallelTime = (endParallel - startParallel) / 1e6;

			// Measure Vectorized Multiplication
			runtime.gc();
			long memoryBeforeVectorized = runtime.totalMemory() - runtime.freeMemory();
			long startVectorized = System.nanoTime();
			double[][] resultVectorized = VectorizedMatrixMultiplication.multiply(matrixA, matrixB);
			long endVectorized = System.nanoTime();
			long memoryAfterVectorized = runtime.totalMemory() - runtime.freeMemory();
			double vectorizedTime = (endVectorized - startVectorized) / 1e6;

			// Calculate metrics
			ExecutionMetrics metrics = MetricsCalculator.calculateExecutionMetrics(sequentialTime, parallelTime, vectorizedTime);

			// Print consolidated results
			System.out.println("Execution Results for Size " + size + " x " + size + ":");
			System.out.printf("  Basic Time: %.6f ms\n", sequentialTime);
			System.out.printf("  Basic Memory Usage: %.2f MB\n", (memoryAfterSequential - memoryBeforeSequential) / 1e6);
			System.out.printf("  Parallel Time: %.6f ms\n", parallelTime);
			System.out.printf("  Parallel Memory Usage: %.2f MB\n", (memoryAfterParallel - memoryBeforeParallel) / 1e6);
			System.out.printf("  Vectorized Time: %.6f ms\n", vectorizedTime);
			System.out.printf("  Vectorized Memory Usage: %.2f MB\n", (memoryAfterVectorized - memoryBeforeVectorized) / 1e6);
			System.out.printf("  Parallel Speedup: %.2fx\n", metrics.getSpeedupParallel());
			System.out.printf("  Vectorized Speedup: %.2fx\n", metrics.getSpeedupVectorized());

			System.out.println("---------------------------------------------------");
		}
	}
}

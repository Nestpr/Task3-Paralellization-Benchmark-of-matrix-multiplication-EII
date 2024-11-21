package Benchmark;

import org.example.matrix.BasicMatrixMultiplication;
import org.example.matrix.Matrix;
import org.example.matrix.ParallelMatrixMultiplication;
import org.example.matrix.VectorizedMatrixMultiplication;

public class MemoryUsageMeasurement {

	public static void main(String[] args) {
		int[] sizes = {10, 50, 100, 200, 500, 1000};

		for (int size : sizes) {
			System.out.println("Testing memory usage for size: " + size + " x " + size);

			double[][] matrixA = Matrix.generateMatrix(size, size);
			double[][] matrixB = Matrix.generateMatrix(size, size);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();
			long memoryBeforeBasic = runtime.totalMemory() - runtime.freeMemory();
			BasicMatrixMultiplication.multiply(matrixA, matrixB);
			long memoryAfterBasic = runtime.totalMemory() - runtime.freeMemory();
			System.out.printf("  Basic Memory Usage: %.2f MB\n", (memoryAfterBasic - memoryBeforeBasic) / 1e6);

			runtime.gc();
			long memoryBeforeParallel = runtime.totalMemory() - runtime.freeMemory();
			ParallelMatrixMultiplication.multiply(matrixA, matrixB);
			long memoryAfterParallel = runtime.totalMemory() - runtime.freeMemory();
			System.out.printf("  Parallel Memory Usage: %.2f MB\n", (memoryAfterParallel - memoryBeforeParallel) / 1e6);

			runtime.gc();
			long memoryBeforeVectorized = runtime.totalMemory() - runtime.freeMemory();
			VectorizedMatrixMultiplication.multiply(matrixA, matrixB);
			long memoryAfterVectorized = runtime.totalMemory() - runtime.freeMemory();
			System.out.printf("  Vectorized Memory Usage: %.2f MB\n", (memoryAfterVectorized - memoryBeforeVectorized) / 1e6);

			System.out.println("---------------------------------------------------");
		}
	}
}


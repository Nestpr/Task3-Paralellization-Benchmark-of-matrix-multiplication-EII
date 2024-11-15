package org.example.matrix;
import java.util.stream.IntStream;
public class VectorizedMatrixMultiplication {

	private static final int BLOCK_SIZE = 64;

	public static double[][] multiply(double[][] A, double[][] B) {
		int n = A.length;
		double[][] result = new double[n][n];

		IntStream.range(0, n / BLOCK_SIZE).parallel().forEach(block -> {
			int start = block * BLOCK_SIZE;
			int end = Math.min(start + BLOCK_SIZE, n);

			for (int i = start; i < end; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						result[i][j] += A[i][k] * B[k][j];
					}
				}
			}
		});

		return result;
	}
}

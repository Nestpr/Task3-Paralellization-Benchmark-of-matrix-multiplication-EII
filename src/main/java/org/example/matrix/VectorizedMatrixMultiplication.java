package org.example.matrix;
public class VectorizedMatrixMultiplication {

	private static final int BLOCK_SIZE = 64;

	public static double[][] multiply(double[][] A, double[][] B) {
		int n = A.length;
		double[][] result = new double[n][n];

		for (int rowBlock = 0; rowBlock < n; rowBlock += BLOCK_SIZE) {
			for (int colBlock = 0; colBlock < n; colBlock += BLOCK_SIZE) {
				for (int kBlock = 0; kBlock < n; kBlock += BLOCK_SIZE) {
					int rowEnd = Math.min(rowBlock + BLOCK_SIZE, n);
					int colEnd = Math.min(colBlock + BLOCK_SIZE, n);
					int kEnd = Math.min(kBlock + BLOCK_SIZE, n);

					for (int i = rowBlock; i < rowEnd; i++) {
						for (int j = colBlock; j < colEnd; j++) {
							double sum = 0.0;

							int k = kBlock;
							for (; k <= kEnd - 8; k += 8) {
								sum += A[i][k] * B[k][j]
										+ A[i][k + 1] * B[k + 1][j]
										+ A[i][k + 2] * B[k + 2][j]
										+ A[i][k + 3] * B[k + 3][j]
										+ A[i][k + 4] * B[k + 4][j]
										+ A[i][k + 5] * B[k + 5][j]
										+ A[i][k + 6] * B[k + 6][j]
										+ A[i][k + 7] * B[k + 7][j];
							}
							for (; k < kEnd; k++) {
								sum += A[i][k] * B[k][j];
							}

							result[i][j] += sum;
						}
					}
				}
			}
		}

		return result;
	}
}

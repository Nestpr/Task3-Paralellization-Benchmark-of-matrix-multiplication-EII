package org.example.matrix;
public class BasicMatrixMultiplication {
	public static double[][] multiply(double[][] A, double[][] B) {
		int n = A.length;
		double[][] result = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					result[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return result;
	}
}

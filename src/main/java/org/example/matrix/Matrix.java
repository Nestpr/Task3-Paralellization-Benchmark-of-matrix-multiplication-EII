package org.example.matrix;
public class Matrix {
	public static double[][] generateMatrix(int rows, int cols) {
		double[][] matrix = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = Math.random();
			}
		}
		return matrix;
	}

	public static boolean compareMatrices(double[][] matrixA, double[][] matrixB) {
		if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) return false;
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixA[0].length; j++) {
				if (Math.abs(matrixA[i][j] - matrixB[i][j]) > 1e-6) return false;
			}
		}
		return true;
	}
}

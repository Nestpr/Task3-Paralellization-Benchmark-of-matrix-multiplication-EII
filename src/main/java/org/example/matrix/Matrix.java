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
}

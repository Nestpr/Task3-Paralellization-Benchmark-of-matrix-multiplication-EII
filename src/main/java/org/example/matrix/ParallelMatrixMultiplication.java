package org.example.matrix;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
public class ParallelMatrixMultiplication {

	private static final int BLOCK_SIZE = 64;
	private static final int THRESHOLD = 200; // Threshold for switching to sequential

	public static double[][] multiply(double[][] A, double[][] B) {
		int n = A.length;
		if (n < THRESHOLD) {
			return BasicMatrixMultiplication.multiply(A, B);
		}

		double[][] result = new double[n][n];
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new MatrixMultiplyTask(A, B, result, 0, n, 0, n, 0, n));
		return result;
	}

	private static class MatrixMultiplyTask extends RecursiveTask<Void> {
		private final double[][] A, B, result;
		private final int rowStart, rowEnd, colStart, colEnd, kStart, kEnd;

		public MatrixMultiplyTask(double[][] A, double[][] B, double[][] result,
								  int rowStart, int rowEnd, int colStart, int colEnd, int kStart, int kEnd) {
			this.A = A;
			this.B = B;
			this.result = result;
			this.rowStart = rowStart;
			this.rowEnd = rowEnd;
			this.colStart = colStart;
			this.colEnd = colEnd;
			this.kStart = kStart;
			this.kEnd = kEnd;
		}

		@Override
		protected Void compute() {
			int rowSize = rowEnd - rowStart;
			int colSize = colEnd - colStart;
			int kSize = kEnd - kStart;

			if (rowSize <= BLOCK_SIZE && colSize <= BLOCK_SIZE && kSize <= BLOCK_SIZE) {
				for (int i = rowStart; i < rowEnd; i++) {
					for (int j = colStart; j < colEnd; j++) {
						double sum = 0;
						for (int k = kStart; k < kEnd; k++) {
							sum += A[i][k] * B[k][j];
						}
						result[i][j] += sum;
					}
				}
				return null;
			}

			int rowMid = (rowStart + rowEnd) / 2;
			int colMid = (colStart + colEnd) / 2;
			int kMid = (kStart + kEnd) / 2;

			invokeAll(
					new MatrixMultiplyTask(A, B, result, rowStart, rowMid, colStart, colMid, kStart, kMid),
					new MatrixMultiplyTask(A, B, result, rowStart, rowMid, colMid, colEnd, kMid, kEnd),
					new MatrixMultiplyTask(A, B, result, rowMid, rowEnd, colStart, colMid, kMid, kEnd),
					new MatrixMultiplyTask(A, B, result, rowMid, rowEnd, colMid, colEnd, kStart, kMid)
			);

			return null;
		}
	}
}

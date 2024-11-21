package org.example.matrix;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
public class ParallelMatrixMultiplication {

	private static final ForkJoinPool SHARED_POOL = new ForkJoinPool(Math.min(Runtime.getRuntime().availableProcessors(), 4));

	public static double[][] multiply(double[][] A, double[][] B) {
		int n = A.length;
		double[][] result = new double[n][n];

		SHARED_POOL.invoke(new MatrixMultiplyTask(A, B, result, 0, n, 0, n, 0, n, getBlockSize(n)));
		return result;
	}

	private static int getBlockSize(int matrixSize) {
		if (matrixSize <= 200) {
			return 64;
		} else if (matrixSize <= 1000) {
			return 128;
		} else {
			return 256;
		}
	}

	private static class MatrixMultiplyTask extends RecursiveTask<Void> {
		private final double[][] A, B, result;
		private final int rowStart, rowEnd, colStart, colEnd, kStart, kEnd;
		private final int blockSize;

		public MatrixMultiplyTask(double[][] A, double[][] B, double[][] result,
								  int rowStart, int rowEnd, int colStart, int colEnd,
								  int kStart, int kEnd, int blockSize) {
			this.A = A;
			this.B = B;
			this.result = result;
			this.rowStart = rowStart;
			this.rowEnd = rowEnd;
			this.colStart = colStart;
			this.colEnd = colEnd;
			this.kStart = kStart;
			this.kEnd = kEnd;
			this.blockSize = blockSize;
		}

		@Override
		protected Void compute() {
			int rowSize = rowEnd - rowStart;
			int colSize = colEnd - colStart;
			int kSize = kEnd - kStart;

			if (rowSize <= blockSize && colSize <= blockSize && kSize <= blockSize) {
				for (int i = rowStart; i < rowEnd; i++) {
					for (int j = colStart; j < colEnd; j++) {
						double sum = 0.0;
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
					new MatrixMultiplyTask(A, B, result, rowStart, rowMid, colStart, colMid, kStart, kMid, blockSize),
					new MatrixMultiplyTask(A, B, result, rowStart, rowMid, colMid, colEnd, kMid, kEnd, blockSize),
					new MatrixMultiplyTask(A, B, result, rowMid, rowEnd, colStart, colMid, kMid, kEnd, blockSize),
					new MatrixMultiplyTask(A, B, result, rowMid, rowEnd, colMid, colEnd, kStart, kMid, blockSize)
			);

			return null;
		}
	}
}

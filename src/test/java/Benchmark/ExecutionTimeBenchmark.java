package Benchmark;
import org.example.matrix.BasicMatrixMultiplication;
import org.example.matrix.Matrix;
import org.example.matrix.ParallelMatrixMultiplication;
import org.example.matrix.VectorizedMatrixMultiplication;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ExecutionTimeBenchmark {

	@Param({"10", "50", "100", "200", "500", "1000"})
	private int size;

	private double[][] matrixA;
	private double[][] matrixB;

	@Setup(Level.Iteration)
	public void setup() {
		matrixA = Matrix.generateMatrix(size, size);
		matrixB = Matrix.generateMatrix(size, size);
	}

	@Benchmark
	public double[][] benchmarkBasic() {
		return BasicMatrixMultiplication.multiply(matrixA, matrixB);
	}

	@Benchmark
	public double[][] benchmarkParallel() {
		return ParallelMatrixMultiplication.multiply(matrixA, matrixB);
	}

	@Benchmark
	public double[][] benchmarkVectorized() {
		return VectorizedMatrixMultiplication.multiply(matrixA, matrixB);
	}
}

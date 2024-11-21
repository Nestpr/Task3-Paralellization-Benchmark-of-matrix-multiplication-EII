package Benchmark;
import Benchmark.ExecutionTimeBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.*;

public class ExecutionTimeRunner {
	public static void main(String[] args) throws RunnerException {
		// Configure JMH benchmarks
		Options opt = new OptionsBuilder()
				.include(ExecutionTimeBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(1)
				.measurementIterations(3)
				.build();

		Collection<RunResult> results = new Runner(opt).run();

		Map<Integer, Double> basicTimes = new HashMap<>();
		Map<Integer, Double> parallelTimes = new HashMap<>();
		Map<Integer, Double> vectorizedTimes = new HashMap<>();

		for (RunResult result : results) {
			String benchmarkName = result.getPrimaryResult().getLabel();
			double time = result.getPrimaryResult().getScore();

			int size = Integer.parseInt(result.getParams().getParam("size"));

			switch (benchmarkName) {
				case "benchmarkBasic":
					basicTimes.put(size, time);
					break;
				case "benchmarkParallel":
					parallelTimes.put(size, time);
					break;
				case "benchmarkVectorized":
					vectorizedTimes.put(size, time);
					break;
			}
		}

		System.out.println("Execution Results by Matrix Size:");
		System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s\n",
				"Size", "Basic Time (ms)", "Parallel Time (ms)", "Vectorized Time (ms)", "Parallel Speedup", "Vectorized Speedup");

		for (int size : basicTimes.keySet()) {
			double basicTime = basicTimes.getOrDefault(size, Double.NaN);
			double parallelTime = parallelTimes.getOrDefault(size, Double.NaN);
			double vectorizedTime = vectorizedTimes.getOrDefault(size, Double.NaN);

			double parallelSpeedup = !Double.isNaN(parallelTime) && parallelTime > 0 ? basicTime / parallelTime : Double.NaN;
			double vectorizedSpeedup = !Double.isNaN(vectorizedTime) && vectorizedTime > 0 ? basicTime / vectorizedTime : Double.NaN;

			System.out.printf("%-10d %-20.6f %-20.6f %-20.6f %-20.2fx %-20.2fx\n",
					size, basicTime, parallelTime, vectorizedTime, parallelSpeedup, vectorizedSpeedup);
		}
	}
}

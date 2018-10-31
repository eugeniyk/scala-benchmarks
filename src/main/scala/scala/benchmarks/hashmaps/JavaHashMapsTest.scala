package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

import scala.benchmarks.hashmaps.HashMapsState.JavaHashMapBenchmark

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class JavaHashMapsTest {
  @Benchmark
  def javaHashMapGetSuccess(state: JavaHashMapBenchmark): Unit = {
    state.map.get("key1")
  }

  @Benchmark
  def javaHashMapGetFailure(state: JavaHashMapBenchmark): Unit = {
    state.map.get("key0")
  }

  @Benchmark
  def javaHashMapGetOrDefaultSuccess(state: JavaHashMapBenchmark): Unit = {
    state.map.getOrDefault("key1", "value0")
  }

  @Benchmark
  def javaHashMapGetOrDefaultFailure(state: JavaHashMapBenchmark): Unit = {
    state.map.getOrDefault("key0", "value0")
  }
}

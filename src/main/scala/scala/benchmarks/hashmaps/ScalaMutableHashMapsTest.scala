package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import HashMapsState.ScalaMutableHashMapBenchmark
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaMutableHashMapsTest {
  @Benchmark
  def scalaMutableHashMapGetSuccess(state: ScalaMutableHashMapBenchmark) = {
    state.map.get("key1")
  }

  @Benchmark
  def scalaMutableHashMapGetFailure(state: ScalaMutableHashMapBenchmark) = {
    state.map.get("key0")
  }

  @Benchmark
  def scalaMutableHashMapGetOrElseSuccess(state: ScalaMutableHashMapBenchmark) = {
    state.map.getOrElse("key1", "value0")
  }

  @Benchmark
  def scalaMutableHashMapGetOrElseFailure(state: ScalaMutableHashMapBenchmark) = {
    state.map.getOrElse("key0", "value0")
  }
}

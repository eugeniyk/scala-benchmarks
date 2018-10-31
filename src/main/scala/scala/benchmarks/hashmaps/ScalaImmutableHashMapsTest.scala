package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import HashMapsState.ScalaImmutableHashMapBenchmark
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaImmutableHashMapsTest {
  @Benchmark
  def scalaImmutableHashMapGetSuccess(state: ScalaImmutableHashMapBenchmark): Unit = {
    state.map.get("key1")
  }

  @Benchmark
  def scalaImmutableHashMapGetFailure(state: ScalaImmutableHashMapBenchmark): Unit = {
    state.map.get("key0")
  }

  @Benchmark
  def scalaImmutableHashMapGetOrElseSuccess(state: ScalaImmutableHashMapBenchmark): Unit = {
    state.map.getOrElse("key1", "value0")
  }

  @Benchmark
  def scalaImmutableHashMapGetOrElseFailure(state: ScalaImmutableHashMapBenchmark): Unit = {
    state.map.getOrElse("key0", "value0")
  }
}

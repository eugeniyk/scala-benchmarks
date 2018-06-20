package hashmaps

import java.util.concurrent.TimeUnit

import hashmaps.HashMapsState.ScalaImmutableHashMapThread
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaImmutableHashMapsTest {
  @Benchmark
  def scalaImmutableHashMapGetSuccess(state: ScalaImmutableHashMapThread): Unit = {
    state.map.get("key1")
  }

  @Benchmark
  def scalaImmutableHashMapGetFailure(state: ScalaImmutableHashMapThread): Unit = {
    state.map.get("key0")
  }

  @Benchmark
  def scalaImmutableHashMapGetOrElseSuccess(state: ScalaImmutableHashMapThread): Unit = {
    state.map.getOrElse("key1", "value0")
  }

  @Benchmark
  def scalaImmutableHashMapGetOrElseFailure(state: ScalaImmutableHashMapThread): Unit = {
    state.map.getOrElse("key0", "value0")
  }
}

package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

import scala.benchmarks.hashmaps.HashMapsState.ScalaImmutableHashMapBenchmark

/**
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 ScalaImmutableHashMapsAppendTest"
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 ScalaImmutableHashMapsAppendTest -prof gc"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ScalaImmutableHashMapsAppendTest {

  @Benchmark
  def scalaImmutableHashMapAppendEmpty(state: ScalaImmutableHashMapBenchmark) = {
    state.map ++ state.emptyMap
  }

  @Benchmark
  def scalaImmutableHashMapPrependEmpty(state: ScalaImmutableHashMapBenchmark) = {
    state.emptyMap ++ state.map
  }

  @Benchmark
  def scalaImmutableHashMapAppendIfNonEmpty(state: ScalaImmutableHashMapBenchmark) = {
    if (state.emptyMap.nonEmpty) {
      state.map ++ state.emptyMap
    } else state.map
  }
}

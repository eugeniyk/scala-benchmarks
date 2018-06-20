package hashmaps

import java.util.concurrent.TimeUnit

import hashmaps.HashMapsState.JavaHashMapThread
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class JavaHashMapsTest {
  @Benchmark
  def javaHashMapGetSuccess(state: JavaHashMapThread): Unit = {
    state.map.get("key1")
  }

  @Benchmark
  def javaHashMapGetFailure(state: JavaHashMapThread): Unit = {
    state.map.get("key0")
  }

  @Benchmark
  def javaHashMapGetOrDefaultSuccess(state: JavaHashMapThread): Unit = {
    state.map.getOrDefault("key1", "value0")
  }

  @Benchmark
  def javaHashMapGetOrDefaultFailure(state: JavaHashMapThread): Unit = {
    state.map.getOrDefault("key0", "value0")
  }
}

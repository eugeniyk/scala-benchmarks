package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.hashmaps.HashMapsState.ConcurrentHashMapBenchmark

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ConcurrentHashMapsTest {

  @Benchmark
  def concurrentHashMapGetSuccess1(state: ConcurrentHashMapBenchmark) = {
    state.map1.get("key1")
  }

  @Benchmark
  def concurrentHashMapGetOrDefaultSuccess1(state: ConcurrentHashMapBenchmark) = {
    state.map1.getOrDefault("key1", "someVal")
  }

  @Benchmark
  def concurrentHashMapComputeIfAbsentSuccess1(state: ConcurrentHashMapBenchmark) = {
    state.map1.computeIfAbsent("key1", state.mapFunc)
  }

  @Benchmark
  def concurrentHashMapComputeIfAbsentWithOptimizationSuccess1(state: ConcurrentHashMapBenchmark) = {
    val value = state.map1.get("key1")
    if (value != null)
      value
    else
      state.map1.computeIfAbsent("key1", state.mapFunc)
  }

  @Benchmark
  def concurrentHashMapGetFailure1(state: ConcurrentHashMapBenchmark) = {
    state.map1.get("key0")
  }

  @Benchmark
  def concurrentHashMapGetOrDefaultFailure1(state: ConcurrentHashMapBenchmark) = {
    state.map1.getOrDefault("key0", "value0")
  }

  @Benchmark
  def concurrentHashMapGetSuccess16(state: ConcurrentHashMapBenchmark) = {
    state.map16.get("key1")
  }

  @Benchmark
  def concurrentHashMapGetOrDefaultSuccess16(state: ConcurrentHashMapBenchmark) = {
    state.map16.getOrDefault("key1", "someVal")
  }

  @Benchmark
  def concurrentHashMapComputeIfAbsentSuccess16(state: ConcurrentHashMapBenchmark) = {
    state.map16.computeIfAbsent("key1", state.mapFunc)
  }

  @Benchmark
  def concurrentHashMapComputeIfAbsentWithOptimizationSuccess16(state: ConcurrentHashMapBenchmark) = {
    val value = state.map16.get("key1")
    if (value != null)
      value
    else
      state.map1.computeIfAbsent("key1", state.mapFunc)
  }

  @Benchmark
  def concurrentHashMapGetFailure16(state: ConcurrentHashMapBenchmark) = {
    state.map16.get("key0")
  }

  @Benchmark
  def concurrentHashMapGetOrDefaultFailure16(state: ConcurrentHashMapBenchmark) = {
    state.map16.getOrDefault("key0", "value0")
  }
}
package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.hashmaps.HashMapsState._

//  sbt "jmh:run -i 10 -wi 5 -f1 -t1 BoxingTest -prof gc"
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class BoxingTest {
  @Benchmark
  def getSuccessMJava(bh: Blackhole, state: JavaHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      val str = state.intMap.get(1)
      bh.consume(Option(str))
      i -= 1
    }
  }

  @Benchmark
  def getFailureMJava(bh: Blackhole, state: JavaHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      val str = state.intMap.get(1)
      bh.consume(Option(str))
      i -= 1
    }
  }

  @Benchmark
  def getSuccessMScala(bh: Blackhole, state: ScalaMutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(1))
      i -= 1
    }
  }

  @Benchmark
  def getFailureMScala(bh: Blackhole, state: ScalaMutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(-1))
      i -= 1
    }
  }

  @Benchmark
  def getSuccessImScala(bh: Blackhole, state: ScalaImmutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(1))
      i -= 1
    }
  }

  @Benchmark
  def getFailureImScala(bh: Blackhole, state: ScalaImmutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(-1))
      i -= 1
    }
  }

  @Benchmark
  def getSuccessDebox(bh: Blackhole, state: DeboxMutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(1))
      i -= 1
    }
  }

  @Benchmark
  def getFailureDebox(bh: Blackhole, state: DeboxMutableHashMapBenchmark) = {
    var i = state.cyclesForGC
    while (i > 0) {
      bh.consume(state.intMap.get(-1))
      i -= 1
    }
  }
}
package scala.benchmarks.time

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class TimeMeasurementTest {
  @Benchmark
  def currentTimeMillis() = {
    System.currentTimeMillis()
  }

  @Benchmark
  def nanoTime() = {
    System.nanoTime()
  }
}

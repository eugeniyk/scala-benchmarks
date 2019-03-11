package scala.benchmarks.collections

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

/**
  * This benchmarks is to find out whether it's better to use Map.empty vs Map()
  *
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 EmptyCollectionsTest.* -prof gc"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class EmptyCollectionsTest {
  @Benchmark
  def emptyMapMethod() = {
    Map.empty[String, String]
  }

  @Benchmark
  def emptyMapApply() = {
    Map[String, String]()
  }
}

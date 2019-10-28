package scala.benchmarks.hashmaps

import java.util
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.collections.ListCreationTest.Item
import scala.benchmarks.hashmaps.SetContainsTest.SetContainsTestState
import scala.collection.mutable

object SetContainsTest {
  @State(Scope.Benchmark)
  class SetContainsTestState() {
    @Param(Array("10", "100", "1000", "10000"))
    var size: Int = _
    var key: String = _

    var scalaSet: Set[String] = _
    var scalaMSet: mutable.Set[String] = _
    var javaSet: java.util.HashSet[String] = _

    @Setup()
    def setup(): Unit = {
      val elements = 1.to(size).map(i => "key"+i.toString)
      key = "key"+size/2

      scalaSet = elements.toSet
      scalaMSet = mutable.HashSet[String](elements: _*)
      javaSet = new util.HashSet[String]()
      elements.foreach { el => javaSet.add(el) }
    }
  }
}

/**
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 SetContainsTest"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class SetContainsTest {
  @Benchmark
  def scalaSetContains(state: SetContainsTestState) = {
    state.scalaSet.contains(state.key)
  }

  @Benchmark
  def scalaMSetContains(state: SetContainsTestState) = {
    state.scalaMSet.contains(state.key)
  }

  @Benchmark
  def javaSetContains(state: SetContainsTestState) = {
    state.javaSet.contains(state.key)
  }
}

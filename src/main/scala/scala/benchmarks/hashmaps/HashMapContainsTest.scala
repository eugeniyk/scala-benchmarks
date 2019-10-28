package scala.benchmarks.hashmaps

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.collections.ListCreationTest.Item
import scala.benchmarks.hashmaps.HashMapContainsTest.HashMapContainsTestState
import scala.collection.mutable

object HashMapContainsTest {
  @State(Scope.Benchmark)
  class HashMapContainsTestState() {
    @Param(Array("10", "100", "1000", "10000"))
    var size: Int = _
    var key: String = _

    var scalaMap: Map[String, Item] = _
    var scalaMMap: mutable.Map[String, Item] = _
    var javaMap: java.util.HashMap[String, Item] = _

    @Setup()
    def setup(): Unit = {
      val elements = 1.to(size).map(i => "key"+i.toString -> Item(i))
      key = "key"+size/2

      scalaMap = elements.toMap
      scalaMMap = mutable.HashMap[String, Item](elements: _*)
      javaMap = new java.util.HashMap()
      elements.foreach { case (k, v) => javaMap.put(k, v) }
    }
  }
}

/**
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 HashMapContainsTest"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class HashMapContainsTest {
  @Benchmark
  def scalaMapContains(state: HashMapContainsTestState) = {
    state.scalaMap.contains(state.key)
  }

  @Benchmark
  def scalaMMapContains(state: HashMapContainsTestState) = {
    state.scalaMMap.contains(state.key)
  }

  @Benchmark
  def javaMapContains(state: HashMapContainsTestState) = {
    state.javaMap.containsKey(state.key)
  }
}

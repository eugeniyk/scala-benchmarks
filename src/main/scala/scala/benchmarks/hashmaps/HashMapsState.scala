package scala.benchmarks.hashmaps

import java.util
import java.util.concurrent.ConcurrentHashMap

import org.openjdk.jmh.annotations.{Scope, State}

import scala.collection.mutable

object HashMapsState {
  abstract class HashMapBenchmark {
    val capacity = 1000
    val cyclesForGC = 10000
  }

  @State(Scope.Benchmark)
  class ConcurrentHashMapBenchmark extends HashMapBenchmark {
    val map1 = new ConcurrentHashMap[String, String](capacity, 0.9f, 1)
    val map16 = new ConcurrentHashMap[String, String]()
    for (i <- 1 to 1000) {
      map1.put("key" + i, "value" + i)
      map16.put("key" + i, "value" + i)
    }

    val mapFunc = new java.util.function.Function[String, String] {
      override def apply(t: String): String = t
    }
  }

  @State(Scope.Benchmark)
  class JavaHashMapBenchmark extends HashMapBenchmark  {
    val map = new util.HashMap[String, String]()
    val intMap = new util.HashMap[Int, String]()

    for (i <- 1 to capacity) {
      map.put("key" + i, "value" + i)
      intMap.put(i, "value" + i)
    }
  }

  @State(Scope.Benchmark)
  class ScalaMutableHashMapBenchmark extends HashMapBenchmark  {
    val map = new mutable.HashMap[String, String]()
    val intMap = new mutable.HashMap[Int, String]()

    for (i <- 1 to capacity) {
      map.put("key" + i, "value" + i)
      intMap.put(i, "value" + i)
    }
  }

  @State(Scope.Benchmark)
  class ScalaImmutableHashMapBenchmark extends HashMapBenchmark {
    val map: Map[String, String] = (1 to capacity).map(i => s"key$i" -> s"value$i").toMap
    val intMap: Map[Int, String] = (1 to capacity).map(i => i -> s"value$i").toMap

    val emptyMap: Map[String, String] = Map.empty
  }

  @State(Scope.Benchmark)
  class DeboxMutableHashMapBenchmark extends HashMapBenchmark {
    val map = debox.Map.empty[String, String]
    val intMap = debox.Map.empty[Int, String]

    for (i <- 1 to capacity) {
      map += ("key" + i, "value" + i)
      intMap += (i, "value" + i)
    }

    map.compact()
    intMap.compact()
  }
}

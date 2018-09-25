package scala.benchmarks.hashmaps

import java.util
import java.util.concurrent.ConcurrentHashMap

import org.openjdk.jmh.annotations.{Scope, State}

import scala.collection.mutable

object HashMapsState {
  @State(Scope.Thread)
  class ConcurrentHashMapThread {
    val map1 = new ConcurrentHashMap[String, String](1000, 0.9f, 1)
    val map16 = new ConcurrentHashMap[String, String]()
    for (i <- 1 to 1000) {
      map1.put("key" + i, "value" + i)
      map16.put("key" + i, "value" + i)
    }

    val mapFunc = new java.util.function.Function[String, String] {
      override def apply(t: String): String = t
    }
  }

  @State(Scope.Thread)
  class JavaHashMapThread {
    val map = new util.HashMap[String, String]()
    for (i <- 1 to 1000) {
      map.put("key" + i, "value" + i)
    }
  }

  @State(Scope.Thread)
  class ScalaMutableHashMapThread {
    val map = new mutable.HashMap[String, String]()
    for (i <- 1 to 1000) {
      map.put("key" + i, "value" + i)
    }
  }

  @State(Scope.Thread)
  class ScalaImmutableHashMapThread {
    val map: Map[String, String] = (1 to 1000).map(i => s"key$i" -> s"value$i").toMap
  }
}

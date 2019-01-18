package scala.benchmarks.system

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.system.ArrayCopyTestState.Arrays
import scala.util.Random

object ArrayCopyTestState {

  @State(Scope.Thread)
  class Arrays {
    @Param(Array("1024", "10240", "102400", "1048576"))
    var arraySize: Int = 0

    var array: Array[Byte] = _
    var emptyArray: Array[Byte] = _

    @Setup()
    def setup(): Unit = {
      array = new Array[Byte](arraySize)
      emptyArray = new Array[Byte](arraySize)
      Random.nextBytes(array)
    }
  }
}

/**
  * sbt "jmh:run -i 10 -wi 3 -f1 -t1 ArrayCopyTest"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ArrayCopyTest {
  @Benchmark
  def scalaArrayCopy(state: Arrays, bh: Blackhole) = {
    Array.copy(state.array, 0, state.emptyArray, 0, state.array.length)
    bh.consume(state.emptyArray)
  }

  @Benchmark
  def javaArrayCopy(state: Arrays, bh: Blackhole) = {
    System.arraycopy(state.array, 0, state.emptyArray, 0, state.array.length)
    bh.consume(state.emptyArray)
  }
}

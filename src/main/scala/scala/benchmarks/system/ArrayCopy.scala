package scala.benchmarks.system

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.system.ArrayCopyTestState.Arrays
import scala.util.Random

object ArrayCopyTestState {

  @State(Scope.Thread)
  class Arrays {
    final val array1k = {
      val msg = new Array[Byte](1000)
      Random.nextBytes(msg)
      msg
    }

    final val array10k = {
      val msg = new Array[Byte](10000)
      Random.nextBytes(msg)
      msg
    }

    final val array100k = {
      val msg = new Array[Byte](100000)
      Random.nextBytes(msg)
      msg
    }

    final val array1kk = {
      val msg = new Array[Byte](1000000)
      Random.nextBytes(msg)
      msg
    }

    final val emptyArray1k = new Array[Byte](1000)
    final val emptyArray10k = new Array[Byte](10000)
    final val emptyArray100k = new Array[Byte](100000)
    final val emptyArray1kk = new Array[Byte](1000000)
  }
}


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ArrayCopyTest {

  @Benchmark
  def scalaArrayCopy1k(state: Arrays, bh: Blackhole) = {
    Array.copy(state.array1k, 0, state.emptyArray1k, 0, state.array1k.length)
    bh.consume(state.emptyArray1k)
  }

  @Benchmark
  def scalaArrayCopy10k(state: Arrays, bh: Blackhole) = {
    Array.copy(state.array10k, 0, state.emptyArray10k, 0, state.array10k.length)
    bh.consume(state.emptyArray10k)
  }

  @Benchmark
  def scalaArrayCopy100k(state: Arrays, bh: Blackhole) = {
    Array.copy(state.array100k, 0, state.emptyArray100k, 0, state.array100k.length)
    bh.consume(state.emptyArray100k)
  }

  @Benchmark
  def scalaArrayCopy1kk(state: Arrays, bh: Blackhole) = {
    Array.copy(state.array1kk, 0, state.emptyArray1kk, 0, state.array1kk.length)
    bh.consume(state.emptyArray1kk)
  }

  @Benchmark
  def javaArrayCopy1k(state: Arrays, bh: Blackhole) = {
    System.arraycopy(state.array1k, 0, state.emptyArray1k, 0, state.array1k.length)
    bh.consume(state.emptyArray1k)
  }

  @Benchmark
  def javaArrayCopy10k(state: Arrays, bh: Blackhole) = {
    System.arraycopy(state.array10k, 0, state.emptyArray10k, 0, state.array10k.length)
    bh.consume(state.emptyArray10k)
  }

  @Benchmark
  def javaArrayCopy100k(state: Arrays, bh: Blackhole) = {
    System.arraycopy(state.array100k, 0, state.emptyArray100k, 0, state.array100k.length)
    bh.consume(state.emptyArray100k)
  }

  @Benchmark
  def javaArrayCopy1kk(state: Arrays, bh: Blackhole) = {
    System.arraycopy(state.array1kk, 0, state.emptyArray1kk, 0, state.array1kk.length)
    bh.consume(state.emptyArray1kk)
  }
}

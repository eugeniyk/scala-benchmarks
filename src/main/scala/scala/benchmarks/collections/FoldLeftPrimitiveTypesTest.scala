package scala.benchmarks.collections

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.collections.FoldLeftPrimitiveTypesTest.CollectionState

object FoldLeftPrimitiveTypesTest {
  case class Data(string: String, int: Int)

  @State(Scope.Benchmark)
  class CollectionState() {
    private val item = Data("test", 1)

    @Param(Array("10", "1000"))
    var collectionSize: Int = 0

    var listCollection: List[Data] = List.empty
    var vectorCollection: Vector[Data] = Vector.empty
    var arrayCollection: Array[Data] = Array.empty

    @Setup()
    def setup(): Unit = {
      listCollection = List.fill(collectionSize)(item)
      vectorCollection = Vector.fill(collectionSize)(item)
      arrayCollection = Array.fill(collectionSize)(item)
    }
  }

  implicit class CollectionSpecialized[T](collection: TraversableOnce[T]) {
    def getISum(map: T => Int): Int = {
      var sum = 0
      for (el <- collection) { sum += map(el) }
      sum
    }

    def getDSum(map: T => Double): Double = {
      var sum = 0.0
      for (el <- collection) { sum += map(el) }
      sum
    }

    def getLSum(map: T => Long): Long = {
      var sum = 0L
      for (el <- collection) { sum += map(el) }
      sum
    }
  }
}

/**
  * This benchmarks is to find out the garbage generated by foldLeft with primitive types
  *
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 FoldLeftPrimitiveTypesTest.* -prof gc"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class FoldLeftPrimitiveTypesTest {

  @Benchmark
  def regularFoldList(bh: Blackhole, state: CollectionState) = {
    bh.consume(
      state.listCollection.foldLeft(0)((acc, item) => acc + item.int)
    )
  }

  @Benchmark
  def regularFoldVector(bh: Blackhole, state: CollectionState) = {
    bh.consume(
      state.vectorCollection.foldLeft(0)((acc, item) => acc + item.int)
    )
  }

  @Benchmark
  def regularFoldArray(bh: Blackhole, state: CollectionState) = {
    bh.consume(
      state.arrayCollection.foldLeft(0)((acc, item) => acc + item.int)
    )
  }

  @Benchmark
  def specializedFoldList(bh: Blackhole, state: CollectionState) = {
    bh.consume(
      state.listCollection.getISum(_.int)
    )
  }

  @Benchmark
  def specializedFoldVector(bh: Blackhole, state: CollectionState) = {
    bh.consume(
      state.vectorCollection.getISum(_.int)
    )
  }

  @Benchmark
  def specializedFoldArray(bh: Blackhole, state: CollectionState) = {
    var sum = 0L
    for (el <- state.arrayCollection) { sum += el.int }
    bh.consume(sum)
  }
}
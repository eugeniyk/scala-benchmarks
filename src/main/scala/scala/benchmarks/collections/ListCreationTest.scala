package scala.benchmarks.collections

import java.util
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.collections.ListCreationTest._
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object ListCreationTest {
  case class Item(id: Int)

  @State(Scope.Benchmark)
  class TestState() {
    @Param(Array("10", "100", "1000"))
    var size: Int = _

    var seq: Seq[Item] = _

    @Setup()
    def setup(): Unit = {
      seq = 1.to(size).map(Item.apply)
    }
  }
}


/**
  * sbt "jmh:run -i 7 -wi 3 -f1 -t1 ListCreationTest.*"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ListCreationTest {
  @Benchmark
  def standardWay(state: TestState) = {
    state.seq.toList
  }

  @Benchmark
  def viaMutableList(state: TestState) = {
    val mList = new mutable.ListBuffer[Item]
    state.seq.foreach { item => mList += item }
    mList
  }

  @Benchmark
  def viaArrayBuffer(state: TestState) = {
    val arrayBuffer = new ArrayBuffer[Item](state.seq.size)
    state.seq.foreach { item => arrayBuffer += item }
    arrayBuffer
  }

  @Benchmark
  def viaArrayBufferNoSizeHint(state: TestState) = {
    val arrayBuffer = new ArrayBuffer[Item]()
    state.seq.foreach { item => arrayBuffer += item }
    arrayBuffer
  }

  @Benchmark
  def foldFunctionalWayPrepend(state: TestState) = {
    state.seq.foldLeft(List.empty[Item])((acc, s) => s :: acc)
  }

  @Benchmark
  def usingJavaReturnScala(state: TestState) = {
    val javaAL = new util.ArrayList[Item](state.seq.size)
    state.seq.foreach { item => javaAL.add(item) }
    javaAL.asScala
  }

  @Benchmark
  def usingJavaReturnScalaNoSizeHint(state: TestState) = {
    val javaAL = new util.ArrayList[Item]()
    state.seq.foreach { item => javaAL.add(item) }
    javaAL.asScala
  }
}

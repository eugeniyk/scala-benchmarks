package scala.benchmarks.options

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.options.GetOrElseDoubleTest.GetOrElseDoubleState

object GetOrElseDoubleTest {
  @State(Scope.Benchmark)
  class GetOrElseDoubleState {
    val doubleOpt = Some(42d)
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@Measurement(batchSize = 1000)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class GetOrElseDoubleTest {
  @Benchmark
  def getOrElseDouble(state: GetOrElseDoubleState) = {
    state.doubleOpt.getOrElse(0d)
  }

  @Benchmark
  def getOrElseDoubleNoGeneric(state: GetOrElseDoubleState) = {
    if (state.doubleOpt.isDefined) state.doubleOpt.get else 0d
  }
}

package scala.benchmarks.JMM

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.JMM.ObjectLazyState._

object ObjectLazyState {
  @State(Scope.Benchmark)
  class StateWithoutLazy {
    val string = "abcdef"
  }

  @State(Scope.Benchmark)
  class StateWithLazy {
    lazy val string = "abcdef"
  }

  @State(Scope.Benchmark)
  class StateWithVar {
    var string = "abcdef"
  }

  @State(Scope.Benchmark)
  class StateWithVarVolatile {
    @volatile var string = "abcdef"
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class LazyTest {
  @Benchmark
  def getStringNonLazy(state: StateWithoutLazy) = {
    state.string
  }

  @Benchmark
  def getStringLazy(state: StateWithLazy) = {
    state.string
  }

  @Benchmark
  def getStringVar(state: StateWithVar) = {
    state.string
  }

  @Benchmark
  def getStringVarVolatile(state: StateWithVarVolatile) = {
    state.string
  }
}

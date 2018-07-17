package scala.benchmarks.JMM

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.JMM.ObjectLazyState.{StateWithLazy, StateWithoutLazy}

object ObjectLazyState {
  @State(Scope.Thread)
  class StateWithoutLazy {
    val string = "abcdef"
  }

  @State(Scope.Thread)
  class StateWithLazy {
    lazy val string = "abcdef"
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class LazyTest {
  @Benchmark
  def getStringNonLazy(state: StateWithoutLazy) = {
    Blackhole.consumeCPU(8)
    state.string
  }

  @Benchmark
  def getStringLazy(state: StateWithLazy) = {
    Blackhole.consumeCPU(8)
    state.string
  }
}

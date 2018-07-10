package scala.benchmarks.JMM

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.JMM.ObjectFinalsState.{StateWithFinal, StateWithoutFinal}

object ObjectFinalsState {
  @State(Scope.Thread)
  class StateWithoutFinal {
    val string = "abcdef"
  }

  @State(Scope.Thread)
  class StateWithFinal {
    final val string = "abcdef"
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ObjectFinalsTest {
  @Benchmark
  def getStringNonFinal(state: StateWithoutFinal): Unit = {
    Blackhole.consumeCPU(8)
    state.string.length
  }

  @Benchmark
  def getStringFinal(state: StateWithFinal): Unit = {
    Blackhole.consumeCPU(8)
    state.string.length
  }
}

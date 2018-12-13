package scala.benchmarks.options

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.options.OptionsTest.OptionsState

object OptionsTest {
  @State(Scope.Benchmark)
  class OptionsState {
    val integer = 42
    val condition: Int => Boolean = _ > 0
    val negativeCondition: Int => Boolean = _ < 0
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class OptionsTest {
  @Benchmark
  def optionPositiveCondition(state: OptionsState) = {
    Option(state.integer).filter(state.condition)
  }

  @Benchmark
  def optionNegativeCondition(state: OptionsState) = {
    Option(state.integer).filter(state.negativeCondition)
  }

  @Benchmark
  def inlinedPositiveCondition(state: OptionsState) = {
    if (state.condition(state.integer)) Some(state.integer) else None
  }

  @Benchmark
  def inlinedNegativeCondition(state: OptionsState) = {
    if (state.negativeCondition(state.integer)) Some(state.integer) else None
  }
}

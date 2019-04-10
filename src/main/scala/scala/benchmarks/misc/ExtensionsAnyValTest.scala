package scala.benchmarks.misc

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.misc.RandomTestState.{StateWithJavaRandom, StateWithScalaRandom}

object ExtensionsAnyValTest {
  case class TestState(str: String)

  implicit class StateHelper1(state: TestState) {
    def getString1: String = state.str
  }

  implicit class StateHelper2(val state: TestState) extends AnyVal {
    def getString2: String = state.str
  }

  @State(Scope.Benchmark)
  class AnyValState() {
    val state = TestState("check")
  }
}

/**
  * sbt "jmh:run -i 10 -wi 3 -f1 -t1 ExtensionsAnyValTest -prof gc"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ExtensionsAnyValTest {
  import ExtensionsAnyValTest._

  @Benchmark
  def useRegularExtension(state: AnyValState) = {
    state.state.getString1
  }

  @Benchmark
  def useAnyValExtension(state: AnyValState) = {
    state.state.getString2
  }
}

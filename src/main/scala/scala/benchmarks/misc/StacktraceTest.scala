package scala.benchmarks.misc

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.misc.StacktraceTestState.ClassWithMethod

object StacktraceTestState {
  @State(Scope.Thread)
  class ClassWithMethod {
    def getCurrentMethodName(): String = Thread.currentThread.getStackTrace()(2).getMethodName
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class StacktraceTest {
  @Benchmark
  def getCurrentMethodNameViaStacktrace(state: ClassWithMethod) = {
    state.getCurrentMethodName()
  }
}

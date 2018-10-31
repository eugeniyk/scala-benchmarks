package scala.benchmarks.misc

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.misc.ToStringTestState.StateWithString

object ToStringTestState {
  @State(Scope.Thread)
  class StateWithString {
    val string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ToStringTest {
  @Benchmark
  def regularString(bh: Blackhole, data: StateWithString) = {
    bh.consume(data.string)
  }

  @Benchmark
  def regularStringWithToString(bh: Blackhole, data: StateWithString) = {
    bh.consume(data.string.toString)
  }
}

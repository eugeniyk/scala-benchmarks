package scala.benchmarks.misc

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.misc.RandomTestState.{StateWithJavaRandom, StateWithScalaRandom}
import scala.util.Random

object RandomTestState {
  @State(Scope.Thread)
  class StateWithJavaRandom {
    val random = new java.util.Random()
  }

  @State(Scope.Thread)
  class StateWithScalaRandom {
    val random = new Random()
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class RandomTest {
  @Benchmark
  def useJavaThreadRandom() = {
    java.util.concurrent.ThreadLocalRandom.current().nextInt()
  }

  @Benchmark
  def useScalaThreadRandom() = {
    scala.concurrent.forkjoin.ThreadLocalRandom.current().nextInt()
  }

  @Benchmark
  def useJavaRandom(state: StateWithJavaRandom) = {
    state.random.nextInt()
  }

  @Benchmark
  def useScalaRandom(state: StateWithScalaRandom) = {
    state.random.nextInt()
  }
}

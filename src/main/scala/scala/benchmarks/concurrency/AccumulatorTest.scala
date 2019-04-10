package scala.benchmarks.concurrency

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.{AtomicLong, LongAdder}

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.benchmarks.concurrency.AccumulatorTest._

object AccumulatorTest {
  @State(Scope.Benchmark)
  class LongAdderState() {
    val longAdder = new LongAdder()
  }

  @State(Scope.Benchmark)
  class AtomicLongState() {
    val atomicLong = new AtomicLong()
  }
}

/**
  * This benchmarks is to find out when's better to use Long Adder vs AtomicLong for your counters
  *
  * sbt "jmh:run -i 10 -wi 5 -f1 -t1 AccumulatorTest.*"   //  1 thread
  * sbt "jmh:run -i 10 -wi 5 -f1 -t4 AccumulatorTest.*"   //  4 thread
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class AccumulatorTest {
  @Benchmark
  def longAdder(bh: Blackhole, state: LongAdderState) = {
    state.longAdder.add(1)
    bh.consume(1)
  }

  @Benchmark
  def atomicLong(bh: Blackhole, state: AtomicLongState) = {
    state.atomicLong.addAndGet(1)
    bh.consume(1)
  }

  @Benchmark
  def longAdderAndGet(state: LongAdderState) = {
    state.longAdder.add(1)
    state.longAdder.sum()
  }

  @Benchmark
  def atomicLongAndGet(bh: Blackhole, state: AtomicLongState) = {
    state.atomicLong.addAndGet(1)
  }

  @Benchmark
  def baseline(bh: Blackhole, state: LongAdderState) = {
    bh.consume(1)
  }
}

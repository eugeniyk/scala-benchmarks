package scala.benchmarks.system

import java.nio.{ByteBuffer, ByteOrder}
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

/**
  * sbt "jmh:run -i 10 -wi 3 -f1 -t1 ArrayPopulationTest"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ArrayPopulationTest {

  @Benchmark
  def populateViaByteBuffer(state: ArrayCopyTestState.Arrays) = {
    val buffer = ByteBuffer.allocate(16 + state.array.length).order(ByteOrder.LITTLE_ENDIAN)
    buffer.putShort(0, 12)
    buffer.putShort(1, 532)
    buffer.position(16)
    buffer.put(state.array)

    buffer.array()
  }

  @Benchmark
  def populateDirectly(state: ArrayCopyTestState.Arrays) = {
    val result = new Array[Byte](16 + state.array.length)
    val wrapper = ByteBuffer.wrap(result, 0, 16).order(ByteOrder.LITTLE_ENDIAN)

    wrapper.putShort(0, 12)
    wrapper.putShort(1, 532)

    System.arraycopy(state.array, 0, result, 16, state.array.length)
    result
  }
}

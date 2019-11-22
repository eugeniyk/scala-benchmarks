package scala.benchmarks.system

import java.lang.management._
import java.util.concurrent.TimeUnit

import com.sun.management.OperatingSystemMXBean
import org.openjdk.jmh.annotations._

object CPUStatsTest {
  @State(Scope.Benchmark)
  class JMXBean {
    val osBean = ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])
  }
}

/**
  * sbt "jmh:run -i 10 -wi 3 -f1 -t1 CPUStatsTest"
  * sbt "jmh:run -i 10 -wi 3 -f1 -t1 CPUStatsTest -prof jmh.extras.Async:asyncProfilerDir=/Volumes/Data/lib/async-profiler-1.6-macos-x64;dir=/tmp/.;flameGraphDir=/Volumes/Data/lib/flame-graph"
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class CPUStatsTest {
  @Benchmark
  def getCPUStats(state: CPUStatsTest.JMXBean) = {
    state.osBean.getProcessCpuLoad
  }
}

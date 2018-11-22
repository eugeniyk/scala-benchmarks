package scala.benchmarks.xml

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.xml.BuilderTestState.Data

object BuilderTestState {
  case class XmlData(field1: String,
                     field2: Boolean,
                     field3: Int,
                     field4: String,
                     field5: Double,
                     field6: String,
                     field7: String,
                     field8: Int,
                     field9: Boolean,
                     field10: Boolean,
                     field11: Boolean,
                     field12: Double,
                     field13: Option[Int],
                     field14: Option[Int])

  @State(Scope.Benchmark)
  class Data() {
    val xml = XmlData("1000000", true, 6, "1234567_12345678_1_2_3", 1234.56, "XYZ", "AA", 1, true, true, true, 1234.67, Some(5), None)
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class XmlBuilderTest {
  @Benchmark
  def standardScalaXMLBuilder(state: Data): String = {
    scala.xml.Utility.trim(
      <ns1:AbcdefghData xmlns:ns1="http://Abc1234567abcd.xbtterwg.xv">
        <ns1:Abc12345678 type="AbdfvxSFdsngkjsd">
          <ns1:abc1234512345678910>
            <ns1:assdgf>
              <ns1:b1234>
                <source>ABCDEFG_ABC</source>
                <abcdefSource>{state.xml.field13}</abcdefSource>
                <ns1:abcedfg>{if (state.xml.field2) 1 else 0}</ns1:abcedfg>
                <ns1:abcde_123567>ABCDEF_ABC</ns1:abcde_123567>
                <ns1:abcde_12>{state.xml.field4}</ns1:abcde_12>
                <ns1:abcde123456_12345>
                  <ns1:abc123>{state.xml.field6}</ns1:abc123>
                  <ns1:abc12>{"%.2f".format(state.xml.field5)}</ns1:abc12>
                </ns1:abcde123456_12345>
                <ns1:ab_1234>{state.xml.field8}</ns1:ab_1234>
                <ns1:abcdefg_1234>{state.xml.field9}</ns1:abcdefg_1234>
                <ns1:abcdefgh_1234>{state.xml.field10}</ns1:abcdefgh_1234>
                <ns1:abcde12_1234>{state.xml.field11}</ns1:abcde12_1234>
                <ns1:abcd00>{state.xml.field3}</ns1:abcd00>
                <ns1:abcs00>{state.xml.field7}</ns1:abcs00>
                <ns1:zcxaa_abc12>{"%.2f".format(state.xml.field12)}</ns1:zcxaa_abc12>
                {if (state.xml.field14.isDefined) <ns1:abc_1234_12345_123>{state.xml.field14.get}</ns1:abc_1234_12345_123> }
              </ns1:b1234>
              <ns1:abcde_fg>{state.xml.field1}</ns1:abcde_fg>
            </ns1:assdgf>
          </ns1:abc1234512345678910>
        </ns1:Abc12345678>
      </ns1:AbcdefghData>
    ).mkString
  }

  @Benchmark
  def stringFormattingXMLBuilder(state: Data): String = {
    s"""
       |<ns1:AbcdefghData xmlns:ns1="http://Abc1234567abcd.xbtterwg.xv">
       |  <ns1:Abc12345678 type="AbdfvxSFdsngkjsd">
       |    <ns1:abc1234512345678910>
       |      <ns1:assdgf>
       |        <ns1:b1234>
       |          <source>ABCDEFG_ABC</source>
       |          <abcdefSource>${state.xml.field13}</abcdefSource>
       |          <ns1:abcedfg>${if (state.xml.field2) 1 else 0}</ns1:abcedfg>
       |          <ns1:abcde_123567>ABCDEF_ABC</ns1:abcde_123567>
       |          <ns1:abcde_12>${state.xml.field4}</ns1:abcde_12>
       |          <ns1:abcde123456_12345>
       |            <ns1:abc123>${state.xml.field6}</ns1:abc123>
       |            <ns1:abc12>${"%.2f".format(state.xml.field5)}</ns1:abc12>
       |          </ns1:abcde123456_12345>
       |          <ns1:ab_1234>${state.xml.field8}</ns1:ab_1234>
       |          <ns1:abcdefg_1234>${state.xml.field9}</ns1:abcdefg_1234>
       |          <ns1:abcdefgh_1234>${state.xml.field10}</ns1:abcdefgh_1234>
       |          <ns1:abcde12_1234>${state.xml.field11}</ns1:abcde12_1234>
       |          <ns1:abcd00>${state.xml.field3}</ns1:abcd00>
       |          <ns1:abcs00>${state.xml.field7}</ns1:abcs00>
       |          <ns1:zcxaa_abc12>${"%.2f".format(state.xml.field12)}</ns1:zcxaa_abc12>
       |          ${if (state.xml.field14.isDefined) s"<ns1:abc_1234_12345_123>${state.xml.field14.get}</ns1:abc_1234_12345_123>" }
       |        </ns1:b1234>
       |        <ns1:abcde_fg>${state.xml.field1}</ns1:abcde_fg>
       |      </ns1:assdgf>
       |    </ns1:abc1234512345678910>
       |  </ns1:Abc12345678>
       |</ns1:AbcdefghData>
     """.stripMargin
  }
  
  private def append(sb: java.lang.StringBuilder, state: Data) = {
    sb
      .append(s"""<ns1:AbcdefghData xmlns:ns1="http://Abc1234567abcd.xbtterwg.xv">""")
        .append(s"""<ns1:Abc12345678 type="AbdfvxSFdsngkjsd">""")
          .append(s"""<ns1:abc1234512345678910>""")
            .append(s"""<ns1:assdgf>""")
              .append(s"""<ns1:b1234>""")
                .append(s"""<source>ABCDEFG_ABC</source>""")
                .append(s"""<abcdefSource>${state.xml.field13}</abcdefSource>""")
                .append(s"""<ns1:abcedfg>${if (state.xml.field2) 1 else 0}</ns1:abcedfg>""")
                .append(s"""<ns1:abcde_123567>ABCDEF_ABC</ns1:abcde_123567>""")
                .append(s"""<ns1:abcde_12>${state.xml.field4}</ns1:abcde_12>""")
                .append(s"""<ns1:abcde123456_12345>""")
                  .append(s"""<ns1:abc123>${state.xml.field6}</ns1:abc123>""")
                  .append(s"""<ns1:abc12>${"%.2f".format(state.xml.field5)}</ns1:abc12>""")
                .append(s"""</ns1:abcde123456_12345>""")
                .append(s"""<ns1:ab_1234>${state.xml.field8}</ns1:ab_1234>""")
                .append(s"""<ns1:abcdefg_1234>${state.xml.field9}</ns1:abcdefg_1234>""")
                .append(s"""<ns1:abcdefgh_1234>${state.xml.field10}</ns1:abcdefgh_1234>""")
                .append(s"""<ns1:abcde12_1234>${state.xml.field11}</ns1:abcde12_1234>""")
                .append(s"""<ns1:abcd00>${state.xml.field3}</ns1:abcd00>""")
                .append(s"""<ns1:abcs00>${state.xml.field7}</ns1:abcs00>""")
                .append(s"""<ns1:zcxaa_abc12>${"%.2f".format(state.xml.field12)}</ns1:zcxaa_abc12>""")

                if (state.xml.field14.isDefined) sb.append(s"""<ns1:abc_1234_12345_123>${state.xml.field14.get}</ns1:abc_1234_12345_123>""")
      sb
              .append(s"""</ns1:b1234>""")
            .append(s"""<ns1:abcde_fg>${state.xml.field1}</ns1:abcde_fg>""")
          .append(s"""</ns1:assdgf>""")
        .append(s"""</ns1:abc1234512345678910>""")
      .append(s"""</ns1:Abc12345678>""")
    .append(s"""</ns1:AbcdefghData>""")

    sb
  }

  private def appendJava(sb: java.lang.StringBuilder, state: Data) = {
    sb
      .append("""<ns1:AbcdefghData xmlns:ns1="http://Abc1234567abcd.xbtterwg.xv">""")
      .append("""<ns1:Abc12345678 type="AbdfvxSFdsngkjsd">""")
      .append("""<ns1:abc1234512345678910>""")
      .append("""<ns1:assdgf>""")
      .append("""<ns1:b1234>""")
      .append("""<source>ABCDEFG_ABC</source>""")
      .append("""<abcdefSource>""")
      .append(state.xml.field13)
      .append("""</abcdefSource>""")
      .append("""<ns1:abcedfg>""")
      .append(if (state.xml.field2) 1 else 0)
      .append("""</ns1:abcedfg>""")
      .append("""<ns1:abcde_123567>ABCDEF_ABC</ns1:abcde_123567>""")
      .append("""<ns1:abcde_12>""")
      .append(state.xml.field4)
      .append("""</ns1:abcde_12>""")
      .append("""<ns1:abcde123456_12345>""")
      .append("""<ns1:abc123>""")
      .append(state.xml.field6)
      .append("""</ns1:abc123>""")
      .append("""<ns1:abc12>""")
      .append("%.2f".format(state.xml.field5))
      .append("""</ns1:abc12>""")
      .append("""</ns1:abcde123456_12345>""")
      .append("""<ns1:ab_1234>""")
      .append(state.xml.field8)
      .append("""</ns1:ab_1234>""")
      .append("""<ns1:abcdefg_1234>""")
      .append(state.xml.field9)
      .append("""</ns1:abcdefg_1234>""")
      .append("""<ns1:abcdefgh_1234>""")
      .append(state.xml.field10)
      .append("""</ns1:abcdefgh_1234>""")
      .append("""<ns1:abcde12_1234>""")
      .append(state.xml.field11)
      .append("""</ns1:abcde12_1234>""")
      .append("""<ns1:abcd00>""")
      .append(state.xml.field3)
      .append("""</ns1:abcd00>""")
      .append("""<ns1:abcs00>""")
      .append(state.xml.field7)
      .append("""</ns1:abcs00>""")
      .append("""<ns1:zcxaa_abc12>""")
      .append("%.2f".format(state.xml.field12))
      .append("""</ns1:zcxaa_abc12>""")

    if (state.xml.field14.isDefined) {
      sb.append("""<ns1:abc_1234_12345_123>""")
      sb.append(state.xml.field14.get)
      sb.append("""</ns1:abc_1234_12345_123>""")
    }

    sb
      .append("""</ns1:b1234>""")
      .append("""<ns1:abcde_fg>""")
      .append(state.xml.field1)
      .append("""</ns1:abcde_fg>""")
      .append("""</ns1:assdgf>""")
      .append("""</ns1:abc1234512345678910>""")
      .append("""</ns1:Abc12345678>""")
      .append("""</ns1:AbcdefghData>""")

    sb
  }

  @Benchmark
  def javaBasedInterpolator(state: Data): String = {
    val sb = new java.lang.StringBuilder()
    append(sb, state)
    sb.toString
  }

  @Benchmark
  def javaBasedInterpolatorWithPreallocation(state: Data): String = {
    val sb = new java.lang.StringBuilder(2000)
    append(sb, state)
    sb.toString
  }

  @Benchmark
  def javaBasedInterpolatorOptAppend(state: Data): String = {
    val sb = new java.lang.StringBuilder()
    appendJava(sb, state)
    sb.toString
  }

  @Benchmark
  def javaBasedInterpolatorWithPreallocationOptAppend(state: Data): String = {
    val sb = new java.lang.StringBuilder(2000)
    appendJava(sb, state)
    sb.toString
  }
}

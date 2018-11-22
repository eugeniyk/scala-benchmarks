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
}

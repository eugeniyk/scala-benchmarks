package scala.benchmarks.xml

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.xml.XMLParsersState.{CachedParser, EmptyXmlData, XmlData}

object XMLParsersState {
  @State(Scope.Thread)
  class EmptyXmlData {
    final val xml = """<Data xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"></Data>"""
  }

  @State(Scope.Thread)
  class CachedParser {
    val cachedParser = scala.xml.XML.withSAXParser(scala.xml.XML.parser)
  }

  @State(Scope.Thread)
  class XmlData {
    final val xml = """<Data xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <field1>api</field1>
          <field2>12481</field2>
          <field3>5ded6fe</field3>
          <field4>40090TRE</field4>
          <field5>2</field5>
          <field6>1</field6>
          <field7>1</field7>
          <field8>2017-12-23'T'12:30:45</field8>
          <field9>2017-12-25'T'12:30:45</field9>
          <field10>34.56</field10>
          <field11>24.56</field11>
          <field12>65.56</field12>
          <field13>615.56</field13>
          <field14>BTY4658</field14>
          <field15>THB</field15>
          <field16>23</field16>
          <field17>someType</field17>
          <field18>Pull</field18>
        </Data>""".split('\n').map(_.trim.filter(_ >= ' ')).mkString
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class SAXParserTest {
  @Benchmark
  def useStandardScalaXMLParser(state: XmlData): Unit = {
    scala.xml.XML.loadString(state.xml)
  }

  @Benchmark
  def useCachedStandardScalaXMLParser(state: XmlData, parser: CachedParser): Unit = {
    parser.cachedParser.loadString(state.xml)
  }

  @Benchmark
  def useStandardScalaXMLParserEmptyData(state: EmptyXmlData): Unit = {
    scala.xml.XML.loadString(state.xml)
  }

  @Benchmark
  def useCachedStandardScalaXMLParserEmptyData(state: EmptyXmlData, parser: CachedParser): Unit = {
    parser.cachedParser.loadString(state.xml)
  }
}

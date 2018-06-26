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
    final val xml = """<ExternalData xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <source>spapi</source>
          <HotelCode>12481</HotelCode>
          <RoomTypeCode>5ded6fe</RoomTypeCode>
          <BrandCode>40090TRE</BrandCode>
          <Adults>2</Adults>
          <Children>1</Children>
          <Rooms>1</Rooms>
          <CheckIn>2017-12-23'T'12:30:45</CheckIn>
          <CheckOut>2017-12-25'T'12:30:45</CheckOut>
          <AmountBeforeTax>34.56</AmountBeforeTax>
          <Tax>24.56</Tax>
          <ServiceCharge>65.56</ServiceCharge>
          <ExtraPersonRate>615.56</ExtraPersonRate>
          <ChainCode>BTY4658</ChainCode>
          <CurrencyCode>THB</CurrencyCode>
          <RatePlanCode>23</RatePlanCode>
          <RateType>someType</RateType>
          <supplySource>Pull</supplySource>
        </ExternalData>""".split('\n').map(_.trim.filter(_ >= ' ')).mkString
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

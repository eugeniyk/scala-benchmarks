package scala.benchmarks.collections.monoid

import scala.collection.mutable

sealed trait CompareResult

case class ObjectsAreSameCompareResult(totalFields: Int) extends CompareResult
case class DiffCompareResult(properties: Map[String, String], totalFields: Int) extends CompareResult {
  lazy val diffPropertiesCount = properties.size
}

object CompareResult {
  val ignore: CompareResult = ObjectsAreSameCompareResult(0)
  val sameProperty: CompareResult = ObjectsAreSameCompareResult(1)

  implicit val mappingValidationResultMonoid: Monoid[CompareResult] = new Monoid[CompareResult] {
    override val empty: CompareResult = ObjectsAreSameCompareResult(0)
    override def combine(x: CompareResult, y: CompareResult): CompareResult = {
      (x, y) match {
        case (ObjectsAreSameCompareResult(total1), ObjectsAreSameCompareResult(total2)) => ObjectsAreSameCompareResult(total1 + total2)
        case (ObjectsAreSameCompareResult(total1), DiffCompareResult(properties2, total2)) => DiffCompareResult(properties2, total1 + total2)
        case (DiffCompareResult(properties1, total1), ObjectsAreSameCompareResult(total2)) => DiffCompareResult(properties1, total1 + total2)
        case (DiffCompareResult(first, total1), DiffCompareResult(second, total2)) =>
          //  for the same properties we will ignore the intersection to exclude it
          DiffCompareResult(first ++ second, total1 + total2 - first.keySet.intersect(second.keySet).size)
      }
    }
  }

  //  To avoid excessive allocations (for some protos it may exceed 1k) here we provide optimized fold operation
  implicit class OptimizedFoldForCompareResult(val seq: TraversableOnce[CompareResult]) extends AnyVal {
    def foldE: CompareResult = {
      var total = 0
      var diff = 0
      var propertiesBuilder: mutable.Builder[(String, String), Map[String, String]] = null

      seq.foreach {
        case ObjectsAreSameCompareResult(totalFields) => total += totalFields
        case DiffCompareResult(properties, totalFields) =>
          total += totalFields
          diff += properties.size
          if (propertiesBuilder == null) {
            propertiesBuilder = Map.newBuilder[String, String]
          }
          propertiesBuilder ++= properties
      }

      if (diff == 0) {
        total match {
          case 0 => CompareResult.ignore
          case 1 => CompareResult.sameProperty
          case _ => ObjectsAreSameCompareResult(total)
        }
      } else {
        val diffPropertiesResult = propertiesBuilder.result()
        //  to avoid counting same total twice we'll
        DiffCompareResult(diffPropertiesResult, total - (diff - diffPropertiesResult.size))
      }
    }
  }
}

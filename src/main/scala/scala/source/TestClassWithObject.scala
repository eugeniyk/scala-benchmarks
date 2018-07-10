package scala.source

object TestObject {
  val justInt: Int = 0
  final val finalInt: Int = 0

  val justString: String = "abc"
  final val finalString: String = "dfe"
}

class TestObject {
  def testMethod() = {
    println(TestObject.justInt)
    println(TestObject.finalInt)

    println(TestObject.justString)
    println(TestObject.finalString)
  }
}
package scala.benchmarks.reflect

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.reflect.TypeTagsTestState.SimpleClass
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object TypeTagsTestState {
  @State(Scope.Thread)
  class SimpleClass {}

  def getTypeByInstance(obj: Any): String = {
    obj.getClass.getTypeName
  }

  def getTypeByClass[T: TypeTag](obj: T): String = {
    obj.getClass.getTypeName
  }

  def getTypeByTypeOf[T: TypeTag](obj: T): String = {
    typeOf[T].toString
  }

  def getTypeByClassTag[T](implicit clazz: scala.reflect.ClassTag[T]): String = {
    clazz.runtimeClass.getTypeName
  }
}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class TypesTest {
  @Benchmark
  def typeViaTypeTag() = {
    typeOf[SimpleClass].toString
  }

  @Benchmark
  def typeViaClassOf() = {
    classOf[SimpleClass].getTypeName
  }

  @Benchmark
  def getTypeByClassWithTypeTag(state: SimpleClass) = {
    TypeTagsTestState.getTypeByClass(state)
  }

  @Benchmark
  def getTypeByTypeOf(state: SimpleClass) = {
    TypeTagsTestState.getTypeByTypeOf(state)
  }

  @Benchmark
  def getTypeByClassTag(state: SimpleClass) = {
    TypeTagsTestState.getTypeByClassTag[SimpleClass]
  }

  @Benchmark
  def getTypeWithoutTypeTag(state: SimpleClass) = {
    TypeTagsTestState.getTypeByInstance(state)
  }
}

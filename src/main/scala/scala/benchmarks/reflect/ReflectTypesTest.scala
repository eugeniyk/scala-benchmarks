package scala.benchmarks.reflect

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.benchmarks.reflect.ReflectTypesTestState.SimpleClass
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object ReflectTypesTestState {
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

/**
 * sbt "jmh:run -i 10 -wi 5 -f1 -t1 ReflectTypesTest -prof gc"
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ReflectTypesTest {
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
    ReflectTypesTestState.getTypeByClass(state)
  }

  @Benchmark
  def getTypeByTypeOf(state: SimpleClass) = {
    ReflectTypesTestState.getTypeByTypeOf(state)
  }

  @Benchmark
  def getTypeByClassTag(state: SimpleClass) = {
    ReflectTypesTestState.getTypeByClassTag[SimpleClass]
  }

  @Benchmark
  def getTypeWithoutTypeTag(state: SimpleClass) = {
    ReflectTypesTestState.getTypeByInstance(state)
  }
}

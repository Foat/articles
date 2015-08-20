package example

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen._
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

class MapExample[T](val base: List[T]) {
  def map[V](f: T => V): MapExample[V] = MapExample(base.map(f))

  override def toString: String = base.toString()
}

object MapExample {
  def apply[T](base: List[T]) = new MapExample[T](base)
}

object MapExampleSpecification extends Properties("MapExample") {
  type T = MapExample[Int]

  val generator: Gen[T] = for {
    l <- arbitrary[Int]
    v <- oneOf(const(MapExample(Nil)), generator)
  } yield MapExample(l :: v.base)

  implicit lazy val arbT: Arbitrary[T] = Arbitrary(generator)

  property("map example") = forAll { a: T =>
    a.map(v => v * 2).base == (for {v <- a} yield v * 2).base
  }
}
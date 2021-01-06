package scala.benchmarks.collections.monoid

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit m: Monoid[A]): Monoid[A] = m

  implicit class TraversableOnceWithMonoid[A](val seq: TraversableOnce[A]) extends AnyVal {
    def foldM(implicit M: Monoid[A]): A = {
      seq.foldLeft(M.empty)(M.combine)
    }
  }
}

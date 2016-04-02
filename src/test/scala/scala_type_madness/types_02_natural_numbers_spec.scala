package scala_type_madness

import org.scalatest.{MustMatchers, FunSpec}

class types_02_natural_numbers_spec extends FunSpec with MustMatchers {
  val zero = MyNaturalNumberZero
  val one = new MyNaturalNumberPositive(zero)
  val two = new MyNaturalNumberPositive(one)

  describe("Natural Numbers implemnted with OOP") {
    it("should add one to one") {
      one.plus(one) mustBe two
    }

    it("should add zero to two") {
      two.plus(zero) mustBe two
      zero.plus(two) mustBe two
    }
  }

}


package scala_type_madness

import org.scalatest.{MustMatchers, FunSpec}
import shapeless.test.illTyped

class types_02_natural_numbers_using_types extends FunSpec with MustMatchers {

  type Nat1 = PositiveNum[NaturalZero]
  type Nat2 = PositiveNum[Nat1]
  type Nat3 = PositiveNum[Nat2]

  describe("Natural numbers using types") {
    it("are subtype of MyBool") {
      implicitly[NaturalZero <:< NaturalNum]
      implicitly[Nat1 <:< NaturalNum]
      illTyped("implicitly[ NaturalZero =:= PositiveNum ]")
    }

    it("Not return the negation of given type") {
      implicitly[ NaturalZero#Plus[NaturalZero]  =:= NaturalZero ]
      implicitly[ Nat1#Plus[NaturalZero] =:= Nat1 ]
      implicitly[ Nat1#Plus[Nat1] =:= Nat2 ]
    }
  }
}



package scala_type_madness

import org.scalatest.{MustMatchers, FunSpec}

import scala_type_madness.PeanoNum.IsZero

class types_04_peano_num_spec extends FunSpec with MustMatchers {

  type Peano_1 = PeaonPositive[PeaonZero]
  type Peano_2 = PeaonPositive[Peano_1]

  describe("Peano numbers") {
    it("is described by two members") {
      implicitly[PeaonZero <:< PeanoNum]
      implicitly[PeaonPositive[PeaonZero] <:< PeanoNum]
    }

    it("Peano 0 matches sedond parameter type") {
      // you can pass whatever types you like as long they have common super type
      type GoAwayIWaitForMyParam[A] = ChurchFalse // and this one need to accept type param
      type MeMePickMe = ChurchTrue
      type RandomPerson = ChurchBool

      // for Peano 0 match returns second type
      implicitly[PeaonZero#Match[GoAwayIWaitForMyParam, MeMePickMe, RandomPerson] =:= MeMePickMe]
    }

    it("Peano positive numbers matches first parameter type") {
      type PickMeIAcceptSexyParams[A] = ChurchTrue
      type LeaveMeAlone = ChurchFalse
      type BaseType = ChurchBool

      // Match for positive returns first type and passes peano number
      implicitly[Peano_1#Match[PickMeIAcceptSexyParams, LeaveMeAlone, BaseType] =:= PickMeIAcceptSexyParams[PeaonZero]]

      // PickMeIAcceptSexyParams completely ignore argument so we can pass whatever, maybe it is a bug ?
      type Whatever = Peano_2
      implicitly[Peano_1#Match[PickMeIAcceptSexyParams, LeaveMeAlone, BaseType] =:= PickMeIAcceptSexyParams[Whatever]]
    }

    it("Peano can check if it is zero") {
      implicitly[IsZero[PeaonZero] =:= ChurchTrue]
      implicitly[IsZero[Peano_1] =:= ChurchFalse]
    }

  }

  describe("Comparison") {
    it("Match type choose first second or last depends on subtype") {
      trait Profession
      trait Skinning extends Profession
      trait Jewelcrafting extends Profession
      trait Engineering extends Profession

      type FastMakingMoney = GT
      type SlowMakingMoney = LT
      type ModerateMakingMoney = EQ

      implicitly[FastMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:= Skinning]
      implicitly[SlowMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:= Engineering]
      implicitly[ModerateMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:= Jewelcrafting]
    }

    it("gt checks if comparison is exactly greater than") {
      implicitly[GT#gt =:= ChurchTrue]
      implicitly[EQ#gt =:= ChurchFalse]
      implicitly[LT#gt =:= ChurchFalse]
    }

    it("ge checks if comparison is greater or equal") {
      implicitly[GT#ge =:= ChurchTrue]
      implicitly[EQ#ge =:= ChurchTrue]
      implicitly[LT#ge =:= ChurchFalse]
    }

    it("le checks if comparison is less or equal") {
      implicitly[GT#le =:= ChurchFalse]
      implicitly[EQ#le =:= ChurchTrue]
      implicitly[LT#le =:= ChurchTrue]
    }

    it("lt checks if comparison is less") {
      implicitly[GT#lt =:= ChurchFalse]
      implicitly[EQ#lt =:= ChurchFalse]
      implicitly[LT#lt =:= ChurchTrue]
    }
  }

  describe("Peano numbers comparison") {
    it("some examples") {
      implicitly[PeaonZero#Compare[PeaonZero]#eq =:= ChurchTrue]
      implicitly[PeaonZero#Compare[PeaonZero]#lt =:= ChurchFalse]
      implicitly[PeaonZero#Compare[PeaonZero]#gt =:= ChurchFalse]

      implicitly[PeaonZero#Compare[Peano_1]#le =:= ChurchTrue]
    }
  }
}


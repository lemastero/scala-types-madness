package scala_type_madness

import org.scalatest.{MustMatchers, FunSpec}

import scala_type_madness.ChurchBool._

class types_03_church_booleans_spec extends FunSpec with MustMatchers {


  describe("Church Booleans") {
    it("there are two subtypes") {
      implicitly[ ChurchTrue <:< ChurchBool  ]
      implicitly[ ChurchFalse <:< ChurchBool ]
    }

    it("If returns first type for true and otherwise second argument") {
      type IntOrLong[TArg <: ChurchBool] = TArg#If[Int, Long, AnyVal]
      implicitly[ IntOrLong[ChurchTrue]  =:= Int  ]
      implicitly[ IntOrLong[ChurchFalse] =:= Long ]
    }

    it("&& returns first type if both arguments are true") {
      implicitly[ ChurchTrue  && ChurchFalse  =:= ChurchFalse ]
      implicitly[ ChurchTrue  && ChurchTrue   =:= ChurchTrue  ]
      implicitly[ ChurchFalse && ChurchFalse  =:= ChurchFalse ]
      implicitly[ ChurchFalse && ChurchTrue   =:= ChurchFalse ]
    }

    it("|| returns false if both arguments are false") {
      implicitly[ ChurchTrue  || ChurchFalse =:= ChurchTrue  ]
      implicitly[ ChurchTrue  || ChurchTrue  =:= ChurchTrue  ]
      implicitly[ ChurchFalse || ChurchFalse =:= ChurchFalse ]
      implicitly[ ChurchFalse || ChurchTrue  =:= ChurchTrue  ]
    }

    it("Xor returns ture if only one argumen is true") {
      implicitly[ ChurchTrue  Xor ChurchTrue  =:= ChurchFalse ]
      implicitly[ ChurchTrue  Xor ChurchFalse =:= ChurchTrue  ]
      implicitly[ ChurchFalse Xor ChurchTrue  =:= ChurchTrue  ]
      implicitly[ ChurchFalse Xor ChurchFalse =:= ChurchFalse ]
    }

    it("Nor return true only if both arguments are false") {
      implicitly[ ChurchTrue  Nor ChurchTrue  =:= ChurchFalse ]
      implicitly[ ChurchTrue  Nor ChurchFalse =:= ChurchFalse ]
      implicitly[ ChurchFalse Nor ChurchTrue  =:= ChurchFalse ]
      implicitly[ ChurchFalse Nor ChurchFalse =:= ChurchTrue  ]
    }

    it("Nand return false only if both arguments are true") {
      implicitly[ ChurchTrue  Nand ChurchTrue  =:= ChurchFalse ]
      implicitly[ ChurchTrue  Nand ChurchFalse =:= ChurchTrue  ]
      implicitly[ ChurchFalse Nand ChurchTrue  =:= ChurchTrue  ]
      implicitly[ ChurchFalse Nand ChurchFalse =:= ChurchTrue  ]
    }

    it("Not returns oposite value") {
      implicitly[ Not[ChurchTrue]  =:= ChurchFalse ]
      implicitly[ Not[ChurchFalse] =:= ChurchTrue ]

      implicitly[ ChurchTrue && ChurchFalse || Not[ChurchFalse] =:= ChurchTrue ]
    }
  }
}

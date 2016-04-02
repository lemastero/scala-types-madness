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
      implicitly[ ChurchTrue  || ChurchFalse =:= ChurchTrue ]
      implicitly[ ChurchTrue  || ChurchTrue  =:= ChurchTrue ]
      implicitly[ ChurchFalse || ChurchFalse =:= ChurchFalse ]
      implicitly[ ChurchFalse || ChurchTrue  =:= ChurchTrue ]
    }

    it("Not returns oposite value") {
      implicitly[ Not[ChurchTrue]  =:= ChurchFalse ]
      implicitly[ Not[ChurchFalse] =:= ChurchTrue ]

      implicitly[ ChurchTrue && ChurchFalse || Not[ChurchFalse] =:= ChurchTrue ]
    }
  }
}

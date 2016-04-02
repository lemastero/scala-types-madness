package scala_type_madness

import org.scalatest.FunSpec
import shapeless.test.illTyped

class types_01_boolean_using_types_spec extends FunSpec {

  describe("Boolean types") {
    it("are subtype of MyBool") {
      implicitly[MyTrue <:< MyBool]
      implicitly[MyFalse <:< MyBool]
      illTyped("implicitly[ MyTrue =:= MyFalse ]")

      // there are some alternatives:
      // "implicitly[ MyTrue =:= MyFalse ]" mustNot compile // with MustMatchers
      // assertTypeError("implicitly[ MyTrue =:= MyFalse ]")
    }

    it("Not return the negation of given type") {
      implicitly[ MyTrue#Not  =:= MyFalse ]
      implicitly[ MyFalse#Not =:= MyTrue ]
    }

    it("Or return true if at least one argument is true") {
      implicitly[ MyTrue#Or[MyTrue]   =:= MyTrue ]
      implicitly[ MyTrue#Or[MyFalse]  =:= MyTrue ]
      implicitly[ MyFalse#Or[MyTrue]  =:= MyTrue ]
      implicitly[ MyFalse#Or[MyFalse] =:= MyFalse ]
    }

    it("And return false if both arguments are true") {
      implicitly[ MyTrue#And[MyTrue]   =:= MyTrue  ]
      implicitly[ MyTrue#And[MyFalse]  =:= MyFalse ]
      implicitly[ MyFalse#And[MyTrue]  =:= MyFalse ]
      implicitly[ MyFalse#And[MyFalse] =:= MyFalse ]
    }

    it("Xor return false if one argument is true and the other is false") {
      implicitly[ MyTrue#Xor[MyTrue]   =:= MyFalse ]
      implicitly[ MyTrue#Xor[MyFalse]  =:= MyTrue  ]
      implicitly[ MyFalse#Xor[MyTrue]  =:= MyTrue  ]
      implicitly[ MyFalse#Xor[MyFalse] =:= MyFalse ]
    }

    it("Nor return true if both arguments are true") {
      implicitly[ MyTrue#Nor[MyTrue]   =:= MyFalse ]
      implicitly[ MyTrue#Nor[MyFalse]  =:= MyFalse ]
      implicitly[ MyFalse#Nor[MyTrue]  =:= MyFalse ]
      implicitly[ MyFalse#Nor[MyFalse] =:= MyTrue  ]
    }

    it("Nand return false if both arguments are true") {
      implicitly[ MyTrue#Nand[MyTrue]   =:= MyFalse ]
      implicitly[ MyTrue#Nand[MyFalse]  =:= MyTrue  ]
      implicitly[ MyFalse#Nand[MyTrue]  =:= MyTrue  ]
      implicitly[ MyFalse#Nand[MyFalse] =:= MyTrue  ]
    }

    it("Xnor return true if both arguments are the same") {
      implicitly[ MyTrue#Xnor[MyTrue]   =:= MyTrue  ]
      implicitly[ MyTrue#Xnor[MyFalse]  =:= MyFalse ]
      implicitly[ MyFalse#Xnor[MyTrue]  =:= MyFalse ]
      implicitly[ MyFalse#Xnor[MyFalse] =:= MyTrue  ]
    }

    it("Nor is Sheffer function") {
      // NOT A = A NOR A
      implicitly[ MyTrue#Not  =:= MyTrue#Nor[MyTrue]   ]
      implicitly[ MyFalse#Not =:= MyFalse#Nor[MyFalse] ]

      // A AND B = (NOT A) NOR (NOT B) = (A NOR A) NOR (B NOR B)
      implicitly[ MyTrue#And[MyTrue]   =:= MyTrue#Not#Nor[MyTrue#Not]   ]
      implicitly[ MyFalse#And[MyFalse] =:= MyFalse#Not#Nor[MyFalse#Not] ]
      implicitly[ MyTrue#And[MyFalse]  =:= MyTrue#Not#Nor[MyFalse#Not]  ]
      implicitly[ MyFalse#And[MyTrue]  =:= MyFalse#Not#Nor[MyTrue#Not] ]

      // A OR B = NOT(A NOR B) = (A NOR B) NOR (A NOR B)
      implicitly[ MyTrue#Or[MyTrue]   =:= MyTrue#Nor[MyTrue]#Not   ]
      implicitly[ MyTrue#Or[MyFalse]  =:= MyTrue#Nor[MyFalse]#Not  ]
      implicitly[ MyFalse#Or[MyTrue]  =:= MyFalse#Nor[MyTrue]#Not  ]
      implicitly[ MyFalse#Or[MyFalse] =:= MyFalse#Nor[MyFalse]#Not ]
    }

    it("Nand is Sheffer function") {
      // NOT A = A NAND A
      implicitly[ MyTrue#Not  =:= MyTrue#Nand[MyTrue]   ]
      implicitly[ MyFalse#Not =:= MyFalse#Nand[MyFalse] ]

      // A AND B = NOT(A NAND B) = (A NAND B) NAND (A NAND B)
      implicitly[ MyTrue#And[MyTrue]   =:= MyTrue#Nand[MyTrue]#Not   ]
      implicitly[ MyFalse#And[MyFalse] =:= MyFalse#Nand[MyFalse]#Not ]
      implicitly[ MyTrue#And[MyFalse]  =:= MyTrue#Nand[MyFalse]#Not  ]
      implicitly[ MyFalse#And[MyTrue]  =:= MyFalse#Nand[MyTrue]#Not  ]

      // A OR B = (NOT A) NAND (NOT B) = (A NAND A) NAND (B NAND B)
      implicitly[ MyTrue#Or[MyTrue]   =:= MyTrue#Not#Nand[MyTrue#Not]   ]
      implicitly[ MyFalse#Or[MyTrue]  =:= MyFalse#Not#Nand[MyTrue#Not]  ]
      implicitly[ MyTrue#Or[MyFalse]  =:= MyTrue#Not#Nand[MyFalse#Not]  ]
      implicitly[ MyFalse#Or[MyFalse] =:= MyFalse#Not#Nand[MyFalse#Not] ]
    }
  }
}

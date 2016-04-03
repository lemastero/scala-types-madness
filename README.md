# scala-types-madness

Experiments with type level programming in Scala. Inspired by:
- presentation: [Type-level Programming in Scala 101 by Joe Barnes](https://www.youtube.com/watch?v=_-J4YRI1rAw)
- serie of posts ["Type level programming in Scala" on Apocalisp] blog(https://apocalisp.wordpress.com/2010/06/08/type-level-programming-in-scala/)

---
* Custom [boolean type implemented using type level programming](../master/src/main/scala/scala_type_madness/types_01_boolean_using_types.scala)
There are additional boolean operations implemented (and, xor, nand, nor, xnor). Fro comparison purpose there is also [implementation using regular programming techniques](../master/src/main/scala/scala_type_madness/types_01_boolean.scala).

```scala
sealed trait MyBool {
  //...
  type And[That <: MyBool] <: MyBool
  type Xor[That <: MyBool] <: MyBool
  type Nand[That <: MyBool] <: MyBool
  type Nor[That <: MyBool] <: MyBool
  type Xnor[That <: MyBool] <: MyBool
}
```
[with tests](../master/src/test/scala/scala_type_madness/types_01_boolean_using_types_spec.scala)
```scala
    it("Nand return false if both arguments are true") {
      implicitly[ MyTrue#Nand[MyTrue]   =:= MyFalse ]
      implicitly[ MyTrue#Nand[MyFalse]  =:= MyTrue  ]
      implicitly[ MyFalse#Nand[MyTrue]  =:= MyTrue  ]
      implicitly[ MyFalse#Nand[MyFalse] =:= MyTrue  ]
    }
```
* Custom (natural numbers implementation using type level programming)[../master/src/main/scala/scala_type_madness/types_02_natural_numbers_using_types.scala]. Based on recursive definition. There is also [regular - value level - implementation](../master/src/main/scala/scala_type_madness/types_02_natural_numbers.scala)
* TODO

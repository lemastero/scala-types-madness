# scala-types-madness

Experiments with type level programming in Scala. Inspired by:
- presentation: [Type-level Programming in Scala 101 by Joe Barnes](https://www.youtube.com/watch?v=_-J4YRI1rAw)
- serie of posts ["Type level programming in Scala" on Apocalisp blog] (https://apocalisp.wordpress.com/2010/06/08/type-level-programming-in-scala/)

---

* Custom [boolean type implemented using type level programming](../master/src/main/scala/scala_type_madness/types_01_boolean_using_types.scala) 
There are additional boolean operations implemented (and, xor, nand, nor, xnor):
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
[with tests](../master/src/test/scala/scala_type_madness/types_01_boolean_using_types_spec.scala):
```scala
it("Nand returns false if both arguments are true") {
  implicitly[ MyTrue#Nand[MyTrue]   =:= MyFalse ]
  implicitly[ MyTrue#Nand[MyFalse]  =:= MyTrue  ]
  implicitly[ MyFalse#Nand[MyTrue]  =:= MyTrue  ]
  implicitly[ MyFalse#Nand[MyFalse] =:= MyTrue  ]
}
```
For comparison purposes there is also [implementation using regular programming techniques] (../master/src/main/scala/scala_type_madness/types_01_boolean.scala).

* Church encoding of boolean type. Base for definition of boolean operators is "if method". Based on post [Type-Level Programming in Scala, Part 3: Boolean](https://apocalisp.wordpress.com/2010/06/13/type-level-programming-in-scala-part-3-boolean/). In addition to original description there are [some tests](../master/src/test/scala/scala_type_madness/types_03_church_booleans_spec.scala):
```scala
it("there are two subtypes") {
  implicitly[ ChurchTrue <:< ChurchBool  ]
  implicitly[ ChurchFalse <:< ChurchBool ]
}
```
[additional boolean operators](../master/src/main/scala/scala_type_madness/types_03_church_booleans.scala):
```scala
object ChurchBool {
  //...
  type Xor[Left <: ChurchBool, Right <: ChurchBool] = Left#If[Not[Right], Right, ChurchBool]
  type Nor[Left <: ChurchBool, Right <: ChurchBool] = Left#If[ChurchFalse,Not[Right],ChurchBool]
  type Nand[Left <: ChurchBool, Right <: ChurchBool] = Left#If[Not[Right], ChurchTrue, ChurchBool]
}
```
and more tests:
```
it("Nor return true only if both arguments are false") {
  implicitly[ ChurchTrue  Nor ChurchTrue  =:= ChurchFalse ]
  implicitly[ ChurchTrue  Nor ChurchFalse =:= ChurchFalse ]
  implicitly[ ChurchFalse Nor ChurchTrue  =:= ChurchFalse ]
  implicitly[ ChurchFalse Nor ChurchFalse =:= ChurchTrue  ]
}
```
* Impllementation of [Peano numbers](../master/src/test/scala/scala_type_madness/types_04_peano_num_spec.scala) based on blog posts [Type-Level Programming in Scala, Part 4a: Peano number basics](https://apocalisp.wordpress.com/2010/06/16/type-level-programming-in-scala-part-4a-peano-number-basics/). To understand this I have work through [some tests](../master/src/main/scala/scala_type_madness/types_04_peano_num.scala) for Match:
```scala
it("Peano 0 matches sedond parameter type") {
  // you can pass whatever types you like as long they have common super type
  type GoAwayIWaitForMyParam[A] = ChurchFalse // and this one need to accept type param
  type MeMePickMe = ChurchTrue
  type RandomPerson = ChurchBool

  // for Peano 0 match returns second type
  implicitly[PeaonZero#Match[GoAwayIWaitForMyParam, MeMePickMe, RandomPerson] =:= 
    MeMePickMe]
}

it("Peano positive numbers matches first parameter type") {
  type PickMeIAcceptSexyParams[A] = ChurchTrue
  type LeaveMeAlone = ChurchFalse
  type BaseType = ChurchBool

  // Match for positive returns first type and passes peano number
  implicitly[Peano_1#Match[PickMeIAcceptSexyParams, LeaveMeAlone, BaseType] =:= 
    PickMeIAcceptSexyParams[PeaonZero]]

  // PickMeIAcceptSexyParams completely ignore argument 
  // so we can pass whatever, maybe it is a bug ?
  type Whatever = Peano_2 
  implicitly[Peano_1#Match[PickMeIAcceptSexyParams, LeaveMeAlone, BaseType] =:= 
    PickMeIAcceptSexyParams[Whatever]]
}
```
For next part [Type-Level Programming in Scala, Part 4b: Comparing Peano numbers](https://apocalisp.wordpress.com/2010/06/17/type-level-programming-in-scala-part-4b-comparing-peano-numbers/) tests to ilustrate comparison Match
```scala
it("Match type choose first second or last depends on subtype") {
  trait Profession
  trait Skinning extends Profession
  trait Jewelcrafting extends Profession
  trait Engineering extends Profession

  type FastMakingMoney = GT
  type SlowMakingMoney = LT
  type ModerateMakingMoney = EQ

  implicitly[FastMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:=
    Skinning]
  implicitly[SlowMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:=
    Engineering]
  implicitly[ModerateMakingMoney#Match[Engineering, Jewelcrafting, Skinning, Profession] =:=
    Jewelcrafting]
}
```
and coparison predicates:
```scala
it("gt checks if comparison is exactly greater than") {
    implicitly[GT#gt =:= ChurchTrue]
    implicitly[EQ#gt =:= ChurchFalse]
    implicitly[LT#gt =:= ChurchFalse]
  }
```

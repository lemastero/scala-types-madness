package scala_type_madness

// implementation of custom boolean using type level programming
// inspired by talk: Type-level Programming in Scala 101 by Joe Barnes
// https://www.youtube.com/watch?v=_-J4YRI1rAw

sealed trait MyBool {
  type Not <: MyBool
  type Or[That <: MyBool] <: MyBool
  type And[That <: MyBool] <: MyBool
  type Xor[That <: MyBool] <: MyBool
  type Nand[That <: MyBool] <: MyBool
  type Nor[That <: MyBool] <: MyBool
  type Xnor[That <: MyBool] <: MyBool
}

sealed trait MyTrue extends MyBool {
  override type Not = MyFalse
  override type Or[That <: MyBool] = MyTrue
  override type And[That <: MyBool] = That
  override type Xor[That <: MyBool] = That#Not
  override type Nand[That <: MyBool] = And[That]#Not
  override type Nor[That <: MyBool] = Or[That]#Not
  override type Xnor[That <: MyBool] = Xor[That]#Not

}

sealed trait MyFalse extends MyBool {
  override type Not = MyTrue
  override type Or[That <: MyBool] = That
  override type And[That <: MyBool] = MyFalse
  override type Xor[That <: MyBool] = That
  override type Nand[That <: MyBool] = And[That]#Not
  override type Nor[That <: MyBool] = Or[That]#Not
  override type Xnor[That <: MyBool] = Xor[That]#Not
}

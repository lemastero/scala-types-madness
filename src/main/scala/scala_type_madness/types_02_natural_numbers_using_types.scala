package scala_type_madness

// implementation of naturla numbers based on Peaon definition
// using type level programming
// inspired by talk: Type-level Programming in Scala 101 by Joe Barnes
// https://www.youtube.com/watch?v=_-J4YRI1rAw

sealed trait NaturalNum {
  type Plus[That <: NaturalNum] <: NaturalNum
}

sealed trait NaturalZero extends NaturalNum {
  type Plus[That <: NaturalNum] = That
}

sealed trait PositiveNum[Prev <: NaturalNum] extends NaturalNum {
  type Plus[That <: NaturalNum] = PositiveNum[Prev#Plus[That]]
}

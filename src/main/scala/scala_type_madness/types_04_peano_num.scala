package scala_type_madness

// implementation of Peaon numbers
// using type level programming
// inspired by:
//  https://apocalisp.wordpress.com/2010/06/16/type-level-programming-in-scala-part-4a-peano-number-basics/

sealed trait TComparison {
  type Match[TLT <: Common, TEQ <: Common, TGT <: Common, Common] <: Common

  // query result of comparison (using Match) about result
  type gt = Match[ChurchFalse, ChurchFalse, ChurchTrue, ChurchBool]
  type ge = Match[ChurchFalse, ChurchTrue, ChurchTrue, ChurchBool]
  type eq = Match[ChurchFalse, ChurchTrue, ChurchFalse, ChurchBool]
  type le = Match[ChurchTrue, ChurchTrue, ChurchFalse, ChurchBool]
  type lt = Match[ChurchTrue, ChurchFalse, ChurchFalse, ChurchBool]
}

sealed trait GT extends TComparison {
  type Match[TLT <: Common, TEQ <: Common, TGT <: Common, Common] = TGT
}

sealed trait LT extends TComparison {
  type Match[TLT <: Common, TEQ <: Common, TGT <: Common, Common] = TLT
}

sealed trait EQ extends TComparison {
  type Match[TLT <: Common, TEQ <: Common, TGT <: Common, Common] = TEQ
}

object PeanoNum {
  type IsZero[A <: PeanoNum] = A#Match[AkwatsFakse, ChurchTrue, ChurchBool]
  type AkwatsFakse[A] = ChurchFalse
}

sealed trait PeanoNum {
  type Match[TPositive[T <: PeanoNum] <: TCommon, TZero <: TCommon, TCommon] <: TCommon
  type Compare[N <: PeanoNum] <: TComparison
}

sealed trait PeaonZero extends PeanoNum {
  type Match[TPositive[T <: PeanoNum] <: TCommon, TZero <: TCommon, TCommon] = TZero
  type Compare[N <: PeanoNum] = N#Match[AlwaysLT, EQ, TComparison]
  type AlwaysLT[A] = LT
}

sealed trait PeaonPositive[TPrevious <: PeanoNum] extends PeanoNum {
  type Match[TPositive[TPrevious <: PeanoNum] <: TCommon, TZero <: TCommon, TCommon] = TPositive[TPrevious]
  type Compare[NInner <: PeanoNum] = NInner#Match[TPrevious#Compare, GT, TComparison]
}





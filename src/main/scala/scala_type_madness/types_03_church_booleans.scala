package scala_type_madness

// Type level implementation of Church encoding booleans
// based on post on blog Apocalisp by Rúnar Óli
// https://apocalisp.wordpress.com/2010/06/13/type-level-programming-in-scala-part-3-boolean/

object ChurchBool {
  type &&[Left <: ChurchBool, Right <: ChurchBool] = Left#If[Right, ChurchFalse, ChurchBool]
  type ||[Left <: ChurchBool, Right <: ChurchBool] = Left#If[ChurchTrue, Right, ChurchBool]
  type Not[Arg <: ChurchBool] = Arg#If[ChurchFalse, ChurchTrue, ChurchBool]
}

sealed trait ChurchBool {
  type If[TTrue <: Common, TFalse <: Common, Common] <: Common
}

sealed trait ChurchTrue extends ChurchBool {
  type If[TTrue <: TCommon, F <: TCommon, TCommon] = TTrue
}
sealed trait ChurchFalse extends ChurchBool {
  type If[TTrue <: TCommon, TFalse <: TCommon, TCommon] = TFalse
}





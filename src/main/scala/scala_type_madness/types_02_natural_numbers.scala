package scala_type_madness

sealed trait CustomNaturalNum {
  def plus(that:CustomNaturalNum):CustomNaturalNum
}

case object MyNaturalNumberZero extends CustomNaturalNum {
  override def plus(that:CustomNaturalNum) =
    that
}

case class MyNaturalNumberPositive(previous:CustomNaturalNum) extends CustomNaturalNum {
  override def plus(that: CustomNaturalNum): CustomNaturalNum =
    MyNaturalNumberPositive( previous.plus(that) )
}

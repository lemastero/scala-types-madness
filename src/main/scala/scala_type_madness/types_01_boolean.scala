package scala_type_madness

// inspired by talk: Type-level Programming in Scala 101 by Joe Barnes
// https://www.youtube.com/watch?v=_-J4YRI1rAw

/** implementation of custom boolean type using objects */

sealed trait CustomBoolean {
  def not:CustomBoolean
  def or(that:CustomBoolean):CustomBoolean
  def and(that:CustomBoolean):CustomBoolean
  def xor(that:CustomBoolean):CustomBoolean = and(that).not.and(or(that))
  /** Sheffer stroke (false if both arguments are true)  */
  def nand(that:CustomBoolean):CustomBoolean = and(that).not
  /** Pierce's arrow (true if both arguments are false) */
  def nor(that:CustomBoolean):CustomBoolean = or(that).not
  def xnor(that:CustomBoolean):CustomBoolean = xor(that).not
}

case object CustomTrue extends CustomBoolean {
  override val not = CustomFalse
  override def or(that: CustomBoolean) = CustomTrue
  override def and(that: CustomBoolean) = that
}

case object CustomFalse extends CustomBoolean {
  override def not = CustomTrue
  override def or(that: CustomBoolean) = that
  override def and(that: CustomBoolean) = CustomFalse
}

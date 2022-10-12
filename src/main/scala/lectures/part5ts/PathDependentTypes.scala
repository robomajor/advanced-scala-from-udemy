package lectures.part5ts

object PathDependentTypes extends App {

  class Outer {
    class Inner
    object InnerObject
    type InnerType

    def print(i: Inner) = println(i)
    def printGeneral(i: Outer#Inner) = println(i)
  }

  def aMethod: Int = {
    class HelperClass
    2
  }

  // per-instance
  val outer = new Outer
  val inner = new outer.Inner // val inner = new Outer.Inner will not work

  val o = new Outer
//  val otherInner: o.Inner = new outer.Inner // different types

  outer.print(inner)
//  o.print(inner) // won't work

  // path dependent types

  // Outer#Inner
  outer.printGeneral(inner)
  o.printGeneral(inner)

  //
  trait ItemLike {
    type Key
  }

  trait Item[K] extends ItemLike {
    type Key = K
  }
  trait IntItem extends Item[Int]
  trait StringItem extends Item[String]

  def get[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???

  get[IntItem](42)
  get[StringItem]("home")
}

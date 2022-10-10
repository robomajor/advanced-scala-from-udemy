package lectures.part4implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(1, 4, 6, 2, 5).sorted)

  /* Implicits (used as implicits parameters)
  * - val/var
  * - objects
  * - accessor methods = defs with no parentheses
  * */

  // 1 -
  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

  implicit val orderingByName: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  println(persons.sorted)

  /*
  * Implicit scope
  * - normal scope = LOCAL SCOPE
  * - imported scope
  * - companions of all types involved in the method signature
  * */

  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.unitPrice * a.nUnits) < (b.unitPrice * b.nUnits))
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }
  val purchases = List(Purchase(5, 35), Purchase(8, 60), Purchase(25, 11), Purchase(4, 63), Purchase(15, 84))

//  import UnitCountOrdering._
//  import UnitPriceOrdering._

  println(purchases.sorted)
}

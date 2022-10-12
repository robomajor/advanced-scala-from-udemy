package lectures.part5ts

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType // abstract type member
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    type AnimalC = Cat
  }

  val ac = new AnimalCollection
  val dog: ac.AnimalType = ???

  val cat: ac.BoundedAnimal = ???

  val pup: ac.SuperBoundedAnimal = new Dog
  val pus: ac.AnimalC = new Cat

  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  // alternative to generics
  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    override type T = Int
    override def add(element: Int): MyList = ???
  }

  type CatsType = cat.type
  val newCat: CatsType = cat

  // enforce a type to be applicable to some types only
  trait MList {
    type A
    def head: A
    def tail: MList
  }

  trait ApplicableToNumbers {
    type A <: Number
  }

  class CustomList(hd: String, tl: CustomList) extends MList {
    type A = String
    def head = hd
    def tail = tl
  }

  class IntList(hd: Int, tl: IntList) extends MList {
    type A = Int
    def head = hd
    def tail = tl
  }
}

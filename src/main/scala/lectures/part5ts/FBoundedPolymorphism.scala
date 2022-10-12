package lectures.part5ts

object FBoundedPolymorphism extends App {

  //  trait Animal {
  //    def breed: List[Animal]
  //  }
  //
  //  class Cat extends Animal {
  //    override def breed: List[Animal] = ???
  //  }
  //
  //  class Dog extends Animal {
  //    override def breed: List[Animal] = ???
  //  }

  // solution 1 - naive
  //  trait Animal {
  //    def breed: List[Animal]
  //  }
  //
  //  class Cat extends Animal {
  //    override def breed: List[Cat] = ???
  //  }
  //
  //  class Dog extends Animal {
  //    override def breed: List[Cat] = ???
  //  }

  // solution 2 - FBP
//  trait Animal[A <: Animal[A]] { // recursive type: F-Bounded Polymorphism
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }

//  trait Entity[E <: Entity[E]] // ORM
//
//  class Person extends Comparable[Person] {
//    override def compareTo(o: Person): Int = ???
//  }
//
//  class Croc extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }

  // solution 3 - FBP + self types
//  trait Animal[A <: Animal[A]] { self: A => // recursive type: F-Bounded Polymorphism
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }
//
//  trait Fish extends Animal[Fish]
//  class Shark extends Fish {
//    override def breed: List[Animal[Fish]] = ???
//  }

  // solution 4 - type classes
//  trait Animal
//  trait CanBreed[A] {
//    def breed(a: A): List[A]
//  }
//
//  class Dog extends Animal
//  object Dog {
//    implicit object DogsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//
//  implicit class CanBreedOps[A](animal: A) {
//    def breed(implicit canBreed: CanBreed[A]): List[A] = canBreed.breed(animal)
//  }
//
//  val dog = new Dog
//  dog.breed
//
//  // new CanBreedOps[Dog](dog).breed
//  // implicit value to pass to breed: Dog.DogsCanBreed
//
//  class Cat extends Animal
//  object Cat {
//    implicit object CatsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//
//  val cat = new Cat
//  cat.breed // will not work

  // solution 5
  trait Animal[A] { // pure type classes
    def breed(a: A): List[A]
  }

  class Dog
  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  class Cat
  object Cat {
    implicit object CatAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal[A]): List[A] = animalTypeClassInstance.breed(animal)
  }

  val dog = new Dog
  dog.breed

  val cat = new Cat
//  cat.breed
  
}

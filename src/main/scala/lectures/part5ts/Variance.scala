package lectures.part5ts

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Croc extends Animal

  class Cage[T]

  // covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // invariance
  class ICage[T]
//  val icage: ICage[Animal] = new ICage[Cat]

  // contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](animal: T) //invariant

  //covariant position
  class CovariantCage[+T](val animal: T)

//  class ContravariantCage[-T](val animal: T) // Contravariant type T occurs in covariant position in type T of value animal

//  class ContravariantVariableCage[-T](var animal: T) // Contravariant type T occurs in covariant position in type T of value animal

//  class CovariantVariableCage[+T](var animal: T) // Covariant type T occurs in contravariant position in type T of value animal

  class InvariantVariableCage[T](var animal: T)

  trait AnotherCovariantCage[+T] {
//    def addAnimal(animal: T) // Covariant type T occurs in contravariant position in type T of value animal
  }

  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true // this is fine
  }

  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // method arguments are in contravariant position

  // return types
  class PetShop[-T] {
//    def get(isItAPuppy: Boolean): T // method return types are in covariant positions
    def get[S <: T](isItAPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
//  val evilCat = shop.get(true, new Cat) // this is wrong
  class TerraNova extends Dog
  val bigFurry = shop.get(true, new TerraNova)

  // BIG RULE
  // method arguments are in CONTRAVARIANT position
  // method return types are in COVARIANT positions

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  class InvariantParking[T](vehicles: List[T]) {
    def park(vehicle: T): InvariantParking[T] = ???
    def impound(vehicles: List[T]): InvariantParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => InvariantParking[S]): InvariantParking[S] = ???
  }

  class CovariantParking[+T](vehicles: List[T]) {
    def park[S >: T](vehicle: S): CovariantParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CovariantParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CovariantParking[S]): CovariantParking[S] = ???
  }

  class ContravariantParking[-T](vehicles: List[T]) {
    def park(vehicle: T): ContravariantParking[T] = ???
    def impound(vehicles: List[T]): ContravariantParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: R => ContravariantParking[S]): ContravariantParking[S] = ???
  }

  // - use covariance = collections of things
  // - use contravariance = group of actions

  class CovariantParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CovariantParking2[S] = ???
    def impound[S >: T](vehicles: IList[S]): CovariantParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class ContravariantParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): ContravariantParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): ContravariantParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }
}

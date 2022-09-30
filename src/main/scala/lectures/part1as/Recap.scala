package lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  val aCondition: Boolean = false
  val aConditionedVal = if (aCondition) 42 else 65
  // instructions vs expressions

  val aCodeBlock = {
    if (aCondition) 54
    56
  }

  // Unit = void
  val theUnit = println("Hello Scala")

  // functions
  def aFunction(x: Int): Int = x + 1

  // recursion
  @tailrec def factorial(n: Int, accumulator: Int): Int = if (n <= 0) accumulator else factorial(n - 1, n * accumulator)

  // object-oriented programming
  class Animal
  class Dog extends Animal

  val aDog: Animal = new Dog  // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Croc extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("Crunch")
  }

  // method notation
  val aCroc = new Croc
  aCroc eat aDog  // very human

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("yummy")
  }

  // generics
  abstract class MyList[+A] // variance and variance problems

  // singletons and companions
  object MyList

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try/catch/finally
  val throwsExceptions = throw new RuntimeException
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I caught a fag"
  } finally {
    println("some logs")
  }

  // functional programming
  val incrementer = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x +1
  List(1, 2, 3).map(anonymousIncrementer) // HOF

  // for-comprehension
  val pairs = for {
    num <- List(1, 2, 3)
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  // Scala collections
  val aMap = Map("Daniel" -> 785, "Jess" -> 555)

  // "collections": Option and Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
    case _ => "Nevermind"
  }
}

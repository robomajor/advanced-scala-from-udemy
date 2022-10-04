package exercises

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepend operator

  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate two streams

  def forEach(f: A => Unit): Unit

  def map[B](f: A => B): MyStream[B]

  def flatMap[B](f: A => MyStream[B]): MyStream[B]

  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes first n elements out of the stream

  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] = if (isEmpty) acc.reverse else tail.toList(head :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  def isEmpty: Boolean = true

  def head: Nothing = throw new NoSuchElementException

  def tail: MyStream[Nothing] = throw new NoSuchElementException

  def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this) // prepend operator

  def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream // concatenate two streams

  def forEach(f: Nothing => Unit): Unit = ()

  def map[B](f: Nothing => B): MyStream[B] = this

  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this // takes first n elements out of the stream
}

class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {
  def isEmpty: Boolean = false

  override val head: A = hd

  override lazy val tail: MyStream[A] = tl // call by need

  def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)

  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)

  def forEach(f: A => Unit): Unit = {
    f(head)
    tail.forEach(f)
  }

  def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f))

  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  def filter(predicate: A => Boolean): MyStream[A] = if (predicate(head)) new Cons(head, tail.filter(predicate)) else tail.filter(predicate)

  def take(n: Int): MyStream[A] = if(n <= 0) EmptyStream else if (n == 1) new Cons(head, EmptyStream) else new Cons(head, tail.take(n-1))
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = new Cons(start, MyStream.from(generator(start))(generator))
}

object StreamsPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)

  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startFromZero = 0 #:: naturals // naturals.#::(0)
  println(startFromZero.head)

  startFromZero.take(10000).forEach(println)

  // map and flatmap
  println(startFromZero.map(_ * 2).take(100).toList())
  println(startFromZero.flatMap(x => new Cons(x, new Cons(x + 1, EmptyStream))).take(10).toList())

  println(startFromZero.filter(_ < 10).take(10).toList())

  // exercises on streams
  // 1 - stream of Fibonacci numbers
  // 2 - stream of prime numbers with Eratosthenes' sieve

  // #1
  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] = new Cons(first, fibonacci(second, first + second))
  println(fibonacci(1, 1).take(100).toList())

  // #2
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) numbers
    else new Cons(numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))

  println(eratosthenes(MyStream.from(2)(_ + 1)).take(100).toList())

  def sequenceFromValueWithStep(value: Int, step: Int): MyStream[Int] = new Cons(value, sequenceFromValueWithStep(value + step, step))
  println(sequenceFromValueWithStep(2, 4).take(100).toList())

  val sequenceFromThreeWithStepFive = MyStream.from(3)(_ + 5)
  println(sequenceFromThreeWithStepFive.take(30).toList())
}

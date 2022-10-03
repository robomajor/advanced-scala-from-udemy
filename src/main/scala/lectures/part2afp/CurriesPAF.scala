package lectures.part2afp

object CurriesPAF extends App {

  // curried functions
  val superAdder: Int => Int => Int = x => y => x + y

  val add3 = superAdder(3)  // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) // curried function

  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)  // lifting - transforming a method to function (ETA-EXPANSION)

  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc)

  // Partial function application
  val add5 = curriedAdder(5) _  // Int => Int

  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int): Int = x + y

  val add7: Int => Int = y => 7 + y
  val add7_1 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = simpleAddFunction.curried(7)
  val add7_6 = simpleAddFunction(7, _: Int)

  val add7_3 = curriedAddMethod(7) _
  val add7_4 = curriedAddMethod(7)(_)

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x" String => concatenator(hello, x, how are you)
  println(insertName("Japa"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello", x, y)
  println(fillInTheBlanks("Daniel", " Scala is awesome!"))

  def curriedFormatterFunction(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatterFunction("%4.2f") _  // lift
  val seriousFormat = curriedFormatterFunction("%8.6f") _
  val preciseFormat = curriedFormatterFunction("%14.12f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(seriousFormat))
  println(numbers.map(preciseFormat))

  println(numbers.map(curriedFormatterFunction("%14.16f"))) // compiler does sweet eta-expansion for us

  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  byName(23)
  byName(method)
  byName(parenMethod())
//  byName(() => 42)  // not ok
  byName((() => 42)())
//  byName(parenMethod _) // not ok

//  byFunction(45)  // not ok
//  byFunction(method)  // not ok
  byFunction(parenMethod) // compiler does eta-expansion
  byFunction(() => 46)
  byFunction(parenMethod _)
}

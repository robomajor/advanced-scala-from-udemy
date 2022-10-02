package lectures.part2afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 7
    case 3 => 35
  }
  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 7
    case 3 => 35
  } // a partial function value

  println(aPartialFunction(2))
//  println(aPartialFunction(4234)) // this will crash program

  // PF utilities
  println(aPartialFunction.isDefinedAt(53))

  // lift
  val lifted = aPartialFunction.lift  // Int => Option[Int]
  println(lifted(2))
  println(lifted(95)) // this will return None

  val partialFunctionChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(partialFunctionChain(2))
  println(partialFunctionChain(45))

  // PF extends normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOF accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 7
    case 3 => 35
  }
  println(aMappedList)

  /*
  / - partial functions can only have ONE parameter type
   */

  // PF instance
  val partialFunctionInstance = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x > 0 && x < 4

    override def apply(v1: Int): Int = v1 match {
      case 1 => 11
      case 2 => 22
      case 3 => 33
    }
  }

  println(partialFunctionInstance(2))
  println(partialFunctionInstance.isDefinedAt(4))

  // dumb chatbot

  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hello, my name is HAL9000"
    case "goobye" => "eyo"
    case "call mom" => "grow up guy"
  }
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}

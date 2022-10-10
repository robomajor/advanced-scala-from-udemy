package lectures.part4implicits

object Pimp extends App {

  // 2.isPrime

  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit = if (n <= 0) () else {
        function()
        timesAux(n - 1)
      }
      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] = if (n <= 0) List() else concatenate(n - 1) ++ list
      concatenate(value)
    }
  }

  42.isEven // type enrichment = pimping

  implicit class RichString(value: String) {
    def asInt: Int = Integer.valueOf(value)
    def encrypt(cypherDistance: Int): String = value.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("John".encrypt(7))

  3.times(() => println("Scala Rocks"))
  println(4 * List(1, 2))

  // "3" / 4
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  print("6" / 2)

  // equivalent: implicit class RichAltInt(value: Int)
  class RichAltInt(value: Int) {
    implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)
  }

  // danger zone
  implicit def intToBoolean(i: Int): Boolean = i == 1

  // if(n) do_something else do_something_else

  val aConditionedValue = if (2) "OK" else "Something wrong"
  println(aConditionedValue)
}

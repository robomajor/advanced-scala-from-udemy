package lectures.part5ts

object RockingInheritance extends App {

  // convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closeable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  // diamond problem
  trait Animal { def name: String }
  trait Lion extends Animal { override def name: String = "Lion" }
  trait Tiger extends Animal { override def name: String = "Tiger" }
  class Mutant extends Lion with Tiger

  val m = new Mutant
  println(m.name) // Tiger

  // the super problem + type linearization
  trait Cold {
    def print = println("Cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("Green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("Blue")
      super.print
    }
  }

  class Red {
    def print = println("Red")
  }

  class White extends Red with Green with Blue {
    override def print = {
      println("White")
      super.print
    }
  }

  val color = new White
  color.print
}

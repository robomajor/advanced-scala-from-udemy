package lectures.part3concurrency

import java.util.concurrent.Executors

object Intro extends App {

  val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }
  // JVM threads
  val aThread = new Thread(runnable)

  aThread.start() // this will create JVM thread

  runnable.run() // doesn't do anything in parallel

  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))

  threadHello.start()
  threadGoodbye.start() // different runs produce different results

  // executors
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 second")
  })

//  pool.shutdown()
//  pool.execute(() => println("should not appear")) // this throws exception in the calling thread

//  pool.shutdownNow() // will throw exception for every sleeping thread
  println(pool.isShutdown) // if pool is shutdown plain way it will not accept any NEW actions
}

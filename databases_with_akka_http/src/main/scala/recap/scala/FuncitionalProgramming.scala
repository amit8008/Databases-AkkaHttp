package recap.scala

import scala.concurrent.Future
import scala.util.{Failure, Success}

object FuncitionalProgramming extends App {

  val incrementer: Int => Int = (x:Int) => x + 1

  println(incrementer(5))

  println(List(1, 2, 3).map(incrementer))

  /*
  * Advance Scala
  * */

  // Multithreading
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future {
    // long computation here
    // Execute on some other thread
    99 / 0
  }

  future.onComplete {
    case Success(value) => println(s"I found the meaning of the life: $value")
    case Failure(exception) => println(s"I found $exception while searching")
  }

}

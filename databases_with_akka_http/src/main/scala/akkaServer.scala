import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{complete, path}
import com.amit.db.DbConnection
import com.amit.db.DbConnection.{coffees, insertDataInCoffees, movies}
//import com.amit.db.DbConnection.setupFuture
import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object akkaServer extends App {
  val logger = Logger(getClass.getName)
  logger.info("Accessing Database with Akka-http API")
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val route1 =
    path("hello") {
      Directives.get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

  DbConnection.insertResult(insertDataInCoffees)
  DbConnection.printResult(coffees)


  val bindingFuture = Http().newServerAt("localhost", 8080)
    .bind(route1)


  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) //trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
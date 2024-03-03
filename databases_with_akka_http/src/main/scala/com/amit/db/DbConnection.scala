package com.amit.db

import com.typesafe.scalalogging.Logger
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
//import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global


object DbConnection {

  val logger = Logger(getClass.getName)
  val db = Database.forConfig("mySqlDb")
//  val resultSet = db.createSession().prepareStatement("select * from movies").executeQuery()
//  while( resultSet.next()) {
//    println(s"${resultSet.getInt("id")}\t ${resultSet.getString(s"name")}\t${resultSet.getInt("year")}")
//  }

  // Definition of the SUPPLIERS table
  class Suppliers(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
    def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
    def name = column[String]("SUP_NAME")
    def street = column[String]("STREET")
    def city = column[String]("CITY")
    def state = column[String]("STATE")
    def zip = column[String]("ZIP")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, street, city, state, zip)
  }
  val suppliers = TableQuery[Suppliers]

  // Definition of the COFFEES table
  class Coffees(tag: Tag) extends Table[(String, Int, Double, Int, Int)](tag, "COFFEES") {
    def name = column[String]("COF_NAME", O.PrimaryKey)
    def supID = column[Int]("SUP_ID")
    def price = column[Double]("PRICE")
    def sales = column[Int]("SALES")
    def total = column[Int]("TOTAL")
    def * = (name, supID, price, sales, total)
  }
  val coffees = TableQuery[Coffees]
//
//val setup = DBIO.seq(
//  // Create the tables, including primary and foreign keys
//  (suppliers.schema ++ coffees.schema).create,
//
//  // Insert some suppliers
//  suppliers += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199"),
//  suppliers += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460"),
//  suppliers += (150, "The High Ground", "100 Coffee Lane",  "Meadows",      "CA", "93966"),
//  // Equivalent SQL code:
//  // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)
//
//  // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
//  coffees ++= Seq(
//    ("Colombian",         101, 7.99, 0, 0),
//    ("French_Roast",       49, 8.99, 0, 0),
//    ("Espresso",          150, 9.99, 0, 0),
//    ("Colombian_Decaf",   101, 8.99, 0, 0),
//    ("French_Roast_Decaf", 49, 9.99, 0, 0)
//  )
//  // Equivalent SQL code:
//  // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
//)
//
//  db.run(setup)

//  case class Movie(id: Int, name: String, year: Int)
  class Movies(tag: Tag) extends Table[(Int, String, Int)](tag, Option[String]("test01"), "movies") {
    def id = column[Int]("id")
    def name = column[String]("name")
    def year = column[Int]("year")
    def * = (id, name, year)
  }

  val movies = TableQuery[Movies]

  /*
  * onComplete attaches a callback function to the Future.
  *  This function will be invoked once the Future completes,
  *  either successfully (Success) or with an error (Failure).
  * */

  val insertDataInMovies = DBIO.seq(
    movies += (4, "movies4", 2009),
    movies += (5, "movies5", 2003),
    movies ++= Seq(
      (6, "movies6", 2019),
      (7, "movies7", 2015)
    )
  )

  def insertResult()={
    logger.info("Records are inserting")
    db.run(insertDataInMovies)
  }

  def printResult() ={
    db.run(movies.result).onComplete {
      case Success(results) =>
        println("Query completed, processing results.")
        results.foreach(println)
      case Failure(exception) =>
        println(s"Error executing query: $exception")
    }
  }

//  val results = Await.result(db.run(movies.result), Duration.Inf)
//  results.foreach(println)
  logger.info("This file is completed")
//  Thread.sleep(5000)

//  db.close()
}

package org.scalabridge.meetrix

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

import scala.collection.mutable

/*

meetrix list --location=123.23,12.5 --radius=50
meetrix search --keyword=blah --category=tech

 */

object Main {

  def main(argsArgs: Array[String]): Unit = {

    val args = argsArgs.toList

    println(parseInput(args))

  }

  def parseInput(args: List[String]): Either[ParseError, Input] = {

    args match {

      case Nil => Left(ParseError("Command not provided"))

      case firstArg :: others => firstArg match {
        case "list" => parseListCmdInput(others)
        case "search" => parseSearchCmdInput(others)
        case unrecognizedCmd => Left(ParseError(s"Unrecognized command $unrecognizedCmd"))
      }
    }
  }

  def parseListCmdInput(args: List[String]): Either[ParseError, ListCmdInput] = {
    //    val l = List("--location=123.23,12.5", "--radius=50")


//    val location = args.sortWith(_ < _)(0).split("=")(1)
//    val radius = args.sortWith(_ < _)(1).split("=")(1)
//    val LatLong = LatLng(location.split(",")(0).toDouble, location.split(",")(1).toDouble)
//Right(ListCmdInput(Option(LatLong), Option(radius.toInt)))

    val myMap = scala.collection.mutable.Map[String, String]()
    for (arg <- args) {

      val pairs = arg.split("=")

      myMap(pairs(0)) = pairs(1)

    }




    if (myMap.contains("--location") && myMap.contains("--radius")) {

      val radius = myMap("--radius")
      val location = myMap("--location")
      val LatLong = LatLng(location.split(",")(0).toDouble, location.split(",")(1).toDouble)
      Right(ListCmdInput(Option(LatLong), Option(radius.toInt)))

    }
    else if (myMap.contains("--location")) {

      val location = myMap("--location")
      val LatLong = LatLng(location.split(",")(0).toDouble, location.split(",")(1).toDouble)

      Right(ListCmdInput(Option(LatLong), None))

    }
    else Left(ParseError("my message"))


}

  def parseSearchCmdInput(args: List[String]): Either[ParseError, SearchCmdInput] = {


    val myMap = scala.collection.mutable.Map[String, String]()
    for (arg <- args) {

      val pairs = arg.split("=")

      myMap(pairs(0)) = pairs(1)

    }

    if (myMap.contains("--keyword") && myMap.contains("--Category")) {

      val Keyword = myMap("--keyword")
      val Category = myMap("--Category")


      Category match {
        case "Art" => Right(SearchCmdInput(Keyword, Option(Art())))
        case "Tech" =>  Right(SearchCmdInput(Keyword, Option(Tech())))
        case _ => Left(ParseError("Unknown Category"))

      }



    }
    else if (myMap.contains("--keyword")) {
      val Keyword = myMap("--keyword")


      Right(SearchCmdInput(Keyword, None))

    }
    else Left(ParseError("my message"))
  }

}

final case class ParseError(errMessage: String)

sealed trait Input

final case class ListCmdInput(
                                 location: Option[LatLng],
                                 radius: Option[Int]
                               ) extends Input

final case class SearchCmdInput(
                                 keyword: String,
                                 category: Option[Category]
                                 ) extends Input

final case class LatLng(
                         lat: Double,
                         lng: Double
                       )

sealed trait Category
final case class Tech() extends Category
final case class Art() extends Category


package org.scalabridge.meetrix

import Main.parseInput
import org.scalatest.WordSpec


class InputParsingSpec extends WordSpec {

  "parseInput" when {

    "Parsing the list Command line" should {

      "succeed when optional parameters are specified" in {

        val result = parseInput(List("list", "--location=45.45,56.67", "--radius=5"))

        assert(result == Right(ListCmdInput(
          Option(LatLng(45.45, 56.67))
          , Option(5))))

      }

    }

  }


}

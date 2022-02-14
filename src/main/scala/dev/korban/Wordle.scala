package dev.korban

import scala.io.*
import scala.io.StdIn.*
import scala.sys.process.*
import scala.util.Random

object Wordle {
  lazy val wordsFile = "words.txt"
  lazy val words =
    Source.fromInputStream(Wordle.getClass.getClassLoader.getResourceAsStream(wordsFile)).getLines().toSeq

  def finishWordle(): Unit = {
    given expected: String = words(Random.between(0, words.size))

    if !doPlay() then finishWordle()
  }

  private def doPlay(i: Int = 1)(using expected: String): Boolean = {
    if i > 6 then
      false
    else
      print(s"$i. ")
      val guess = readLine()

      if words.contains(guess) then
        println(generateHint(expected, guess))
        if guess == expected then
          true
        else
          doPlay(i+1)
      else
        println("Invalid guess!")
        doPlay(i)
  }

  val greenCode  = "\u001b[42m"
  val yellowCode = "\u001b[48;5;220m"
  val blackCode  = "\u001b[40m"
  val resetCode  = "\u001b[0m"

  private def generateHint(expected: String, guess: String): String = {
    guess.zip(expected).map {
      case (gC, eC) =>
        val color =
          if gC == eC then
            greenCode
          else if expected.contains(gC) then
            yellowCode
          else
            blackCode
        color + gC + resetCode
    }.fold("")(_ + _)
  }

}

package nl.frankivo.iracing

import java.time.LocalDateTime

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model._

object EventReader {

  def parseContent(doc: Browser#DocumentType): List[Race] = {
    val schedule = doc >> element("#src-schedule")
    val races = schedule >> elementList("tbody tr")
    races.map(trToRace)
  }

  def trToRace(tr: Element): Race = {
    val id = tr >> extractor("td.col-num", text, asInt)
    val name = tr >> text("td.col-event")
    val start = getTime(tr >> text("td.col-qualifying"))
    val end = start.plusHours(2).plusMinutes(30)

    Race(id, name, start, end)
  }

  def getTime(td: String): LocalDateTime = {
    val Array(time: String, _, _, month, day, year) = td.split(' ')
    val Array(hour, minute) = time.split(':')
    LocalDateTime.of(toDigit(year), monthNameToNumber(month), toDigit(day), toDigit(hour), toDigit(minute))
  }

  def toDigit(s: String): Int = s.filter(_.isDigit).toInt

  val monthNameToNumber: Map[String, Int] = Map(
    "Jan" -> 1,
    "Feb" -> 2,
    "Mar" -> 3,
    "Apr" -> 4,
    "May" -> 5,
    "Jun" -> 6,
    "Jul" -> 7,
    "Aug" -> 8,
    "Sep" -> 9,
    "Oct" -> 10,
    "Nov" -> 11,
    "Dec" -> 12
  )
}

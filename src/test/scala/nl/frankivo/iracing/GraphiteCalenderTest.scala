package nl.frankivo.iracing

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.junit.jupiter.api.Test

class GraphiteCalenderTest {

  @Test
  def testCreateEvent(): Unit = {
    val r = Race(1, "Race 1", LocalDateTime.ofInstant(Instant.now, ZoneId.of("UTC")).plusHours(3),
      LocalDateTime.ofInstant(Instant.now, ZoneId.of("UTC")).plusHours(9))
    GraphiteCalender.createEvent(r)
  }

  @Test
  def testCreateRealEvent(): Unit = {
    val file = "src/test/resources/s10.html"
    val doc = JsoupBrowser().parseFile(file)

    val race = EventReader.parseContent(doc).head
    GraphiteCalender.createEvent(race)
  }

  @Test
  def dateTimeTest() : Unit = {
    val dt = LocalDateTime.of(2019, 6, 19, 18,30)
    println(dt.toString)
    println(dt.format(DateTimeFormatter.ISO_DATE_TIME))
  }
}

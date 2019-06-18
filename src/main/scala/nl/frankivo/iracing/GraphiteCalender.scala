package nl.frankivo.iracing

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.{Event, EventDateTime}

object GraphiteCalender {

  val calenderName: String = "Graphite Racing"

  private val calender = GoogleCalender.getCalender(calenderName).getOrElse(
    throw new Exception("Calendar with name '%s' not found.".format(calenderName)))

  def createEvent(race: Race): Unit = {
    val event = new Event()
      .setSummary(race.summary)
      .setStart(convertDateTime(race.start))
      .setEnd(convertDateTime(race.end))

    println(event)
    GoogleCalender.insertEvent(event, calender.id)
  }

  def convertDateTime(dateTime: LocalDateTime): EventDateTime = {
   println(dateTime.toString)

    new EventDateTime()
      .setDateTime(new DateTime(dateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
      .setTimeZone("UTC")
  }

}

package nl.frankivo.iracing

import java.time.LocalDateTime

case class Race(
                 id: Int,
                 name: String,
                 start: LocalDateTime,
                 end: LocalDateTime
               ) {
  def summary: String = "Undiecar #%d - %s".format(id, name)
}

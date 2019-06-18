package nl.frankivo.iracing

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class GoogleCalendarTest {

  @Test
  def testCredentials(): Unit = {
    val cred = GoogleCalender.getCredentials(GoogleCalender.HTTP_TRANSPORT)
    assertNotNull(cred)
  }

  @Test
  def testGetItems(): Unit = {
    val events = GoogleCalender.getNextEvents()
    assertTrue(events.length > 0)
  }

  @Test
  def testGetCals(): Unit = {
    val cals = GoogleCalender.getCalenderList
    assertTrue(cals.length > 0)
  }

  @Test
  def testGetCalender(): Unit = {
    val cal = GoogleCalender.getCalender("primary")
    assertTrue(cal.isDefined)
  }
}

package nl.frankivo.iracing

import java.io.{File, FileNotFoundException, InputStreamReader}
import java.util.Collections

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.model.{CalendarListEntry, Event}
import com.google.api.services.calendar.{Calendar, CalendarScopes}

case class CalendarData(id: String, summary: String, desc: Option[String])

case class CalenderEntry(id: String)

object GoogleCalender {
  private val APPLICATION_NAME = "Undiecar"
  private val JSON_FACTORY = JacksonFactory.getDefaultInstance
  private val CREDENTIALS_FILE_PATH = "/credentials.json"
  private val TOKENS_DIRECTORY_PATH = "tokens"
  private val SCOPES = Collections.singletonList(CalendarScopes.CALENDAR)

  val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport
  private val service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build

  def getCalenderList: Array[CalendarData] = {
    service
      .calendarList()
      .list()
      .execute
      .getItems
      .toArray
      .map(r => {
        val item = r.asInstanceOf[CalendarListEntry]
        CalendarData(item.getId, item.getSummary, Option(item.getDescription))
      })
  }

  def getCalender(calenderName: String): Option[CalendarData] = {
    GoogleCalender
      .getCalenderList
      .toList
      .find(c => c.sumary.equals(calenderName))
  }

  def getNextEvents(calendarId: String = "primary"): Array[CalenderEntry] = {
    service.events
      .list(calendarId)
      .setTimeMin(new DateTime(System.currentTimeMillis))
      .setOrderBy("startTime")
      .setSingleEvents(true)
      .execute

      .getItems
      .toArray
      .map(r => {
        val item = r.asInstanceOf[Event]
        CalenderEntry(item.getId)
      })
  }

  def insertEvent(event: Event, calendarId: String = "primary"): Event = service.events.insert(calendarId, event).execute

  def getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential = {
    val in = classOf[Nothing].getResourceAsStream(CREDENTIALS_FILE_PATH)
    if (in == null) throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH)
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in))

    val flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
      .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build
    val receiver = new LocalServerReceiver.Builder().setPort(8888).build
    new AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
  }

}

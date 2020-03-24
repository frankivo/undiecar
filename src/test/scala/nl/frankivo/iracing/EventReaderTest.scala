package nl.frankivo.iracing

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.junit.Assert.assertEquals
import org.junit.Test

class EventReaderTest {
  @Test
  def testParser(): Unit = {
    val file = "src/test/resources/s10.html"
    val doc = JsoupBrowser().parseFile(file)

    val races = EventReader.parseContent(doc)
    assertEquals(9, races.length)
  }
}

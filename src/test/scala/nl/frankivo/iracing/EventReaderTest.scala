package nl.frankivo.iracing

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EventReaderTest {
  @Test
  def testParser(): Unit = {
    val file = "src/test/resources/s10.html"
    val doc = JsoupBrowser().parseFile(file)

    val races = EventReader.parseContent(doc)
    assertEquals(9, races.length)
  }
}

package nl.frankivo.iracing

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import nl.frankivo.iracing.EventReader.parseContent

object main {

  val BaseUrl = "https://undiecar.com/season/"

  def main(args: Array[String]): Unit = {
    val season = args.headOption.getOrElse(throw new Exception("No season defined")).toInt
    val url = BaseUrl + "%d/".format(season)

    val doc = JsoupBrowser().get(url)
    val races = parseContent(doc)
    races.foreach(GraphiteCalender.createEvent)
  }
}


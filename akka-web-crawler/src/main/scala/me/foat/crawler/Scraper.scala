package me.foat.crawler

import java.net.URL

import akka.actor.{Actor, ActorRef}
import org.apache.commons.validator.routines.UrlValidator
import org.jsoup.Jsoup

import scala.collection.JavaConverters._

/**
  * @author Foat Akhmadeev
  *         17/01/16
  */
class Scraper(indexer: ActorRef) extends Actor {
  val urlValidator = new UrlValidator()

  def receive: Receive = {
    case Scrap(url) =>
      println(s"scraping $url")
      val content = parse(url)
      sender() ! ScrapFinished(url)
      indexer ! Index(url, content)
  }

  def parse(url: URL): Content = {
    val link: String = url.toString
    val response = Jsoup.connect(link).ignoreContentType(true)
      .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1").execute()

    val contentType: String = response.contentType
    if (contentType.startsWith("text/html")) {
      val doc = response.parse()
      val title: String = doc.getElementsByTag("title").asScala.map(e => e.text()).head
      val descriptionTag = doc.getElementsByTag("meta").asScala.filter(e => e.attr("name") == "description")
      val description = if (descriptionTag.isEmpty) "" else descriptionTag.map(e => e.attr("content")).head
      val links: List[URL] = doc.getElementsByTag("a").asScala.map(e => e.attr("href")).filter(s =>
        urlValidator.isValid(s)).map(link => new URL(link)).toList
      Content(title, description, links)
    } else {
      // e.g. if this is an image
      Content(link, contentType, List())
    }
  }
}


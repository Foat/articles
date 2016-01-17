package me.foat

import java.net.URL

import scala.language.implicitConversions

/**
  * @author Foat Akhmadeev
  *         17/01/16
  */
package object crawler {
  implicit def string2url(s: String): URL = new URL(s)

  implicit def string2urlWithSpec(s: (String, String)): URL = new URL(new URL(s._1), s._2)
}

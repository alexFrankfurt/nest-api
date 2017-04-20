package services.nestoria

import scala.collection.mutable


class Nestoria(country: Country, action: Action, encoding: String, pretty: Boolean) {

  private val params: mutable.Map[String, String] = new mutable.HashMap

  private val version = "1.22"

  val baseUrl: String = "http://api.nestoria" +
    stringify(country) + "/api?action=" + stringify(action) & "encoding=" +
      encoding & "version=" + version & (if (pretty) "pretty=1" else "")

  def searchFor(s: String): Nestoria = {
    params += (("place_name", s))
    this
  }

  def withParams(ps: Parameter*): Nestoria = {
    params ++= ps map stringify

    this
  }

  def withPlainParams(ps: (String, String)*): Nestoria = {
    params ++= ps
    this
  }

  def url: String = baseUrl + params.map( kv => "&" + kv._1 + "=" + kv._2).mkString
}

object Nestoria {
  def apply(country: Country, action: Action, encoding: String = "json", pretty: Boolean = false): Nestoria =
    new Nestoria(country, action, encoding, pretty)
}

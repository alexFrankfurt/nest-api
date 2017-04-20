package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Key(id: Int, keywords: List[String], title: String, price: Int, propertyType: String, updatedInDays: Short)

object Key {
  def tr(g: Option[(Int, List[String], String, Int, String, Short)]): Option[(String, String, Int, String, Short)] =
    g match {
      case Some((_, k, t, p , pt, uid)) => Some(k.mkString(", "), t, p, pt, uid)
      case None => None
    }


    implicit val customReads: Reads[Key] = (
      (__ \ "id").read(1) and
      (__ \ "keywords").read[String].map(s => s.split(',').map(_.trim).toList) and
      (__ \ "title").read[String] and
      (__ \ "price").read[Int] and
      (__ \ "property_type").read[String] and
      (__ \ "updated_in_days").read[Short]
      )(Key.apply _)

    implicit val customWrites: Writes[Key] = (
      (__ \ "keywords").write[String] and
      (__ \ "title").write[String] and
      (__ \ "price").write[Int] and
      (__ \ "property_type").write[String] and
      (__ \ "updated_in_days").write[Short]
    )(unlift(Key.unapply _ andThen tr))

  val s =
    """
      |  def tr(g: Option[(Int, List[String], String, Int, String, Short)]): Option[(String, String, Int, String, Short)] =
      |    g match {
      |      case Some((_, k, t, p , pt, uid)) => Some(k.mkString(", "), t, p, pt, uid)
      |      case None => None
      |    }
      |
      |    val aaa = 3
    """.stripMargin
}

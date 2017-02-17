package actors

import akka.actor._
import models.db.Tables
import models.db.Tables.PropertiesRow
import play.api.Logger
import play.api.libs.json.JsObject
import services.db.DBService
import play.api.libs.json.{JsArray, Json}
import play.api.libs.ws.WSClient
import services.nestoria._
import utils.db.TetraoPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class RequestActor(ws: WSClient, database: DBService) extends Actor {
  import RequestActor._

  override def receive = {

    case RequestNestoriaData =>
      Logger.info("Requesting data from Nestoria servers")

      val reqUrl = Nestoria(UK, SearchListings, pretty = true)
          .withParams(ListingType("buy"))
          .searchFor("brighton").url

      val request = ws.url(reqUrl).get()

      request.onComplete {
        case Success(response) =>
          Logger.info("Got data from Nestoria server")
          val listings = (response.json \ "response" \ "listings").as[JsArray].value
          val propRows = listings.map(o => Json.fromJson[PropertiesRow](o.as[JsObject])).filter(_.isSuccess).map(_.get)
          database.run(Tables.Properties.delete)
          database.run((Tables.Properties returning Tables.Properties) ++= propRows)

        case Failure(e) =>
          Logger.info("Failure, while requesting data from Nestoria servers.")
          Logger.info(e.getMessage)
      }

    case LookForKeywords(kws) =>
      val queryResult = database.run(Tables.Properties.filter(_.keywords @> kws).result)
      val jsonResult = Json.toJson(queryResult)
      sender() ! jsonResult
  }
}

object RequestActor {
  def props(ws: WSClient, db: DBService) = Props(new RequestActor(ws, db))

  case object RequestNestoriaData
  case class LookForKeywords(keywords: List[String])
}

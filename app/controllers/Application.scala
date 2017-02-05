package controllers

import javax.inject.{Inject, Singleton}

import actors.RequestActor
import actors.RequestActor.GetData
import akka.actor.ActorSystem
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import akka.pattern.ask
import akka.util.Timeout
import models.db.Tables
import models.db.Tables.{customReads, customWrites}
import models.db.Tables.PropertiesRow
import play.api.libs.functional.syntax
import services.db.DBService
import utils.db.TetraoPostgresDriver.api._
import play.api.libs.json._
import models.Key
import play.twirl.api.Html

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class Application @Inject() (ws: WSClient, system: ActorSystem, database: DBService) extends Controller{

  implicit val timeout = Timeout(5 seconds)

  private val requestActor = system.actorOf(RequestActor.props(ws))

  def index() = Action {

    val resp = Await.result(
      ws.url("http://api.nestoria.co.uk/api")
        .withQueryString("encoding" -> "json",
          "action" -> "search_listings",
          "country" -> "uk",
          "listing_type" -> "buy",
          "place_name" -> "brighton",
          "version" -> "1.22").get().map {resp => resp.json}, 5 seconds)

    val lsgs = (resp \ "response" \ "listings").as[JsArray].value
    val data = lsgs.map(o => Json.fromJson[PropertiesRow](o.as[JsObject]))
    val sdata = data.filter(_.isSuccess).map(_.get)
    database.run(Tables.Properties.delete)
    database.run((Tables.Properties returning Tables.Properties) ++= sdata)

    Ok(views.html.main("Nestoria api")(views.html.interactive()))
  }

  def search() = Action(parse.json) { request =>
    val queryData = (request.body \ "keywords").as[List[String]]
    val response = database.run(Tables.Properties.filter(_.keywords @> queryData).result)
    val r = Json.toJson(response)
    Ok(r)
  }
}

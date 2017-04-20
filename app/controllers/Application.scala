package controllers

import javax.inject.Inject

import actors.RequestActor
import actors.RequestActor._
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import models.db.Tables.customReads
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import services.db.DBService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class Application @Inject() (ws: WSClient, system: ActorSystem, database: DBService) extends Controller {

  implicit val timeout = Timeout(5 seconds)

  private val requestActor = system.actorOf(RequestActor.props(ws, database))

  requestActor ! RequestNestoriaData

  def index() = Action {
    Ok(views.html.main("Nestoria api")(views.html.interactive()))
  }

  def search() = Action.async(parse.json) { request =>
    val queryData = (request.body \ "keywords").as[List[String]]
    (requestActor ? LookForKeywords(queryData)).mapTo[JsValue].map(Ok(_))
  }
}

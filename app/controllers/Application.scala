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
import services.db.DBService
import utils.db.TetraoPostgresDriver.api._
import play.api.libs.json._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class Application @Inject() (ws: WSClient, system: ActorSystem, database: DBService) extends Controller{

  implicit val timeout = Timeout(5 seconds)

  private val requestActor = system.actorOf(RequestActor.props(ws))

  def index() = Action {

    val row = Tables.PropertiesRow(1, List("jkfd"), "fldj", 1, "jf", 43)

    val content = Await.result((requestActor ? GetData), 5 seconds).asInstanceOf[String]
    Ok(content)
  }
}

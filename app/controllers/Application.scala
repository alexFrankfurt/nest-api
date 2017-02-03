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

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class Application @Inject() (ws: WSClient, system: ActorSystem, database: DBService) extends Controller{

  implicit val timeout = Timeout(5 seconds)

  private val requestActor = system.actorOf(RequestActor.props(ws))

  def index() = Action {
    val rows = database.run{
      val testData = List("Oha")
      Tables.Properties.filter(_.keywords @> testData).result}
    val res = rows.map {row => row.keywords}
    Ok(res.toString())
  }
}

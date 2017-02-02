package controllers

import javax.inject.{Inject, Singleton}

import actors.RequestActor
import actors.RequestActor.GetData
import akka.actor.ActorSystem
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class Application @Inject() (ws: WSClient, system: ActorSystem) extends Controller{

  implicit val timeout = Timeout(5 seconds)

  private val requestActor = system.actorOf(RequestActor.props(ws))

  def index() = Action.async {
    (requestActor ? GetData).mapTo[String].map { resp =>
      Ok(resp)
    }
  }

}

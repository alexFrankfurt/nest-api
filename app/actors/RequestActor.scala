package actors

import akka.actor._
import play.api.libs.ws.WSClient

import scala.concurrent.{Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object RequestActor {
  def props(ws: WSClient) = Props(new RequestActor(ws))

  case object GetData
}

class RequestActor(ws: WSClient) extends Actor {
  import RequestActor._

  override def receive = {
    case GetData =>
      println("getting data")
      val resp = Await.result(
        ws.url("http://api.nestoria.co.uk/api")
          .withQueryString("encoding" -> "json",
                            "action" -> "search_listings",
                           "country" -> "uk",
                           "listing_type" -> "buy",
                           "place_name" -> "brighton",
                           "version" -> "1.22").get().map {resp => resp.json}, 5 seconds)
      sender() ! (resp \ "response").get.toString()

  }
}

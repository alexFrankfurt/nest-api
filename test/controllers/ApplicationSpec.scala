package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.mvc._
import play.api.test._
import org.specs2.mutable._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import services.db.DBService

import concurrent.Future

class ApplicationSpec extends PlaySpecification with Results {

  val app = new GuiceApplicationBuilder().build()
  val ws = app.injector.instanceOf[WSClient]
  implicit val as = app.injector.instanceOf[ActorSystem]
  val db = app.injector.instanceOf[DBService]
  val controller = new Application(ws, as, db)
  implicit val mat = ActorMaterializer()

  "The Application" should {
    "response with index page" in {
      val result = controller.index()(FakeRequest())
      status(result) must equalTo(200)
    }
//    "return correct json string" in {
//      val req = FakeRequest().withHeaders("Content-type" -> "application/json")
//          .withJsonBody(Json.parse("""{"keywords": ["Kitchen", "Garden"]}"""))
//      val result = controller.search()(req)
//      val bodyText = contentAsString(result.run())
//      bodyText mustEqual(" ")
//    }
  }

}
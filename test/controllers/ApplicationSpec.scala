package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.mvc._
import play.api.test._
import org.specs2.mutable._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsObject, JsValue, Json}
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
  val jsonStringValue = "[{\"keywords\":\"Garden, Fireplace, Kitchen\",\"title\":\"Dudley Road, Brighton Bn1 - Fireplace\",\"price\":400000,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Accessibility, Purpose Built, Wood Floor, Double Bedroom, Garage, Kitchen, Lift, Basement\",\"title\":\"Belle Vue Gardens, Brighton Bn2\",\"price\":300000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Garden, Fireplace, High Ceilings, Victorian, Wood Floor, Double Bedroom, Kitchen, Reception\",\"title\":\"Queens Park Road, Brighton Bn2\",\"price\":749950,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Leasehold, Garden, Refurbished, Double Bedroom, Kitchen\",\"title\":\"Ditchling Rise, Brighton Bn1 - Garden\",\"price\":550000,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Share of Freehold, Shared Garden, Refurbished, Double Bedroom, Kitchen\",\"title\":\"Grand Parade, Brighton Bn2 - Freehold\",\"price\":230000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Garden, Fireplace, Kitchen\",\"title\":\"Ewhurst Road, Brighton, East Sussex Bn2\",\"price\":214500,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Accessibility, Refurbished, Wood Floor, Kitchen, Porter\",\"title\":\"Sussex Heights, Brighton Bn1 - Porter\",\"price\":425000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"No Chain, Double Bedroom, Kitchen\",\"title\":\"Clyde Road, Brighton Bn1 - No Chain\",\"price\":149950,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Leasehold, Double Bedroom, Kitchen\",\"title\":\"York Place, Brighton Bn1 - Leasehold\",\"price\":225000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Leasehold, Conversion, Kitchen, Modern\",\"title\":\"Cavendish Place, Brighton Bn1\",\"price\":390000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Garden, Conversion, Kitchen\",\"title\":\"Coventry Street, Brighton, East Sussex Bn1\",\"price\":595000,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Detached, Garden, En suite, Conservatory, Double Bedroom, Garage, Kitchen, Reception\",\"title\":\"Windmill View, Brighton Bn1 - Garden\",\"price\":600000,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Garden, Lower Ground, Double Bedroom, Kitchen, Patio\",\"title\":\"Lower Rock Gardens, Brighton Bn2\",\"price\":325000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Garden, No Chain, Kitchen\",\"title\":\"St. Georges Road, Brighton Bn2\",\"price\":180000,\"property_type\":\"flat\",\"updated_in_days\":2},{\"keywords\":\"Garden, En suite, Conservatory, Conversion, Fireplace, Sauna, Wood Floor, Kitchen, Dishwasher, Patio\",\"title\":\"Downside, Westdene, Brighton Bn1\",\"price\":550000,\"property_type\":\"house\",\"updated_in_days\":2},{\"keywords\":\"Balcony, Gym, Double Bedroom, Kitchen, Parking\",\"title\":\"Mandalay Court Road, Patcham, Brighton Bn1\",\"price\":275000,\"property_type\":\"flat\",\"updated_in_days\":5},{\"keywords\":\"En suite, Kitchen, Parking\",\"title\":\"The Deco Building, Coombe Road, Brighton Bn2\",\"price\":200000,\"property_type\":\"flat\",\"updated_in_days\":5},{\"keywords\":\"Detached, Garden, Kitchen, Dishwasher, Parking\",\"title\":\"Reigate Road, Brighton Bn1 - Detached\",\"price\":700000,\"property_type\":\"house\",\"updated_in_days\":5}]"

  "The Application" should {
    "response with index page" in {
      val result = controller.index()(FakeRequest())
      status(result) must equalTo(200)
    }
    "return correct json string" in {
      val jsv = Json.obj("keywords" -> Json.arr("Kitchen")) //"""{"keywords": ["Kitchen"]}"""
      val req = FakeRequest("POST", "/search")
          .withHeaders("Content-type" -> "application/json")
          .withBody[JsValue](jsv)
      val result = controller.search()(req)
      val bodyText = contentAsString(result)
      bodyText mustEqual jsonStringValue
    }
  }

}
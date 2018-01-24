package controllers

import java.util.Calendar
import javax.inject._

import Service.JsonService
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(jsonService: JsonService)(ws: WSClient) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */


  def index() = Action { implicit request: Request[AnyContent] =>
    val time = Calendar.getInstance().get(Calendar.SECOND)
    Thread.sleep(5000)

    Ok("Hello  " + time)
  }


  def sampleFuture = Action.async { request =>
    Logger.debug("Request Received")
    val time = Calendar.getInstance().get(Calendar.SECOND)
    val futureResponse: Future[WSResponse] = ws.url("https://jsonplaceholder.typicode.com/posts")
      .get()
    futureResponse.map(res => {
      res.status match {
        case OK =>
          Logger.debug("Response received from WS request")
          Ok(Json.toJson(time))
        case _ => println("No data")
          NotFound
      }
    })
  }

}

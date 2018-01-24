package controllers

import javax.inject.Inject
import play.api.libs.json._
import Service.JsonService
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}

class JsonCheck @Inject()(cc: ControllerComponents)(jsonService: JsonService)(ws: WSClient) extends AbstractController(cc) {
// Method used for checking the Name in firstName and lastName Format
  def jsonInputCheck() = Action { request =>
    Ok(jsonService.validate(request.body.asJson) match {
      case Left(a) =>   jsonService.getJson(a)
      case Right(a) => Json.toJson(a)
    })
  }
}

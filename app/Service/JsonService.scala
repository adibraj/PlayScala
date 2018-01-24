package Service

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._ // Combinator syntax

case class Name(val firstName: String, val lastName: String)

class JsonService {

  implicit val nameRead = ((JsPath \ "name" \ "firstName")).read[String].and((JsPath \ "name" \ "lastName").read[String])(Name.apply _)
  implicit val nameWrite = ((JsPath \ "name" \ "firstName")).write[String].and((JsPath \ "name" \ "lastName").write[String])(unlift(Name.unapply))

  def validate(jsValue: Option[JsValue]): Either[Name, String] = {
    val jsvaluee = Json.toJson(jsValue)
    jsvaluee.validate[Name] match {
      case JsSuccess(a: Name, JsPath) =>
        Left(a)
      case _ =>
        Right("Not A proper Format")
    }

  }
  //Converts the Object to Json takes Implicit write function
  def getJson(name: Name): JsValue =
  {
    Json.toJson(name)
  }



}

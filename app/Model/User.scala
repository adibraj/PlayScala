package Model

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, _}
import play.api.mvc.AnyContent
//Mobile should be of 10 Digit
case class User(val firstName: String, val lastName: String, val mobile: Long)

object UserValidation {
  implicit val nameRead = ((JsPath \ "user" \ "firstName"))
    .read[String](minLength[String](3))
    .and((JsPath \ "user" \ "lastName").read[String](minLength[String](3)))
    .and((JsPath \ "user" \ "mobile").read[Long](min[Long](1)))(User.apply _)
  implicit val nameWrite = ((JsPath \ "user" \ "firstName"))
    .write[String]
    .and((JsPath \ "user" \ "lastName").write[String])
    .and((JsPath \ "user" \ "mobile").write[Long])(unlift(User.unapply))

  def validateObject(jsValue: AnyContent): Either[String, User] = {
    val value = Json.toJson(jsValue.asJson)
    value.validate[User] match {
      case JsSuccess(a: User, path) => {
        if (a.mobile.toString.length < 10 && a.mobile.toString.length > 10)
          Left("InValid")
        else
          Right(a)
      }
      case _ =>
        Left("Error")

    }
  }

}

package Model

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.Reads._
//Mobile should be of 10 Digit
case class User(val id: Option[Int],
                val firstName: String,
                val lastName: String,
                val mobile: Long)

object UserValidation {
  implicit val nameRead = ((JsPath \ "user" \ "id")
    .readNullable[Int])
    .and((JsPath \ "user" \ "firstName")
      .read[String](minLength[String](3)))
    .and((JsPath \ "user" \ "lastName").read[String](minLength[String](3)))
    .and((JsPath \ "user" \ "mobile").read[Long](min[Long](1)))(User.apply _)
  implicit val nameWrite = ((JsPath \ "user" \ "id")
    .writeNullable[Int])
    .and((JsPath \ "user" \ "firstName")
      .write[String])
    .and((JsPath \ "user" \ "lastName").write[String])
    .and((JsPath \ "user" \ "mobile").write[Long])(unlift(User.unapply))
}

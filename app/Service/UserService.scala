package Service

import Model.{User, UserValidation}
import UserValidation._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContent

import scala.collection.mutable.ArrayBuffer

class UserService {
  var listUser: ArrayBuffer[User] = new ArrayBuffer[User]()

  def addUser(jsValue: AnyContent): Boolean = {
    val newUser = validateObject(jsValue);
    newUser match {
      case Left(a)  => false
      case Right(a) => { listUser += (a); true }
    }
  }
  def getAllUsers(): JsValue = {
    Json.toJson(listUser.map(u => Json.toJson(u)(nameWrite)))
  }

  def getUserByUserName(name: String): Option[JsValue] = {
    val userFound = (for (u <- listUser if (u.firstName.equals(name))) yield u)
    if (userFound.length < 1)
      None
    else
      Some(Json.toJson(userFound.head)(nameWrite))

  }

  def getUserByMobile(mobile: Long): Option[JsValue] = {
    val userFound = (for (u <- listUser if (u.mobile.equals(mobile))) yield u)
    if (userFound.length < 1)
      None
    else
      Some(Json.toJson(userFound.head)(nameWrite))

  }

}

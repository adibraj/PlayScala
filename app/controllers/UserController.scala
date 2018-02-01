package controllers

import javax.inject.{Inject, Singleton}

import Model.User
import Model.UserValidation._
import Service.UserService
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class UserController @Inject()(cc: ControllerComponents)(
    userService: UserService)
    extends AbstractController(cc) {

//Mobile should be of 10Digits
  def registerUser() = Action(parse.json) { request =>
    request.body.validate[User] match {
      case JsSuccess(a, _) => {
        userService.addUser(a) match {
          case Some(a) => Ok(Json.toJson(a))
          case _       => InternalServerError
        }
      }
      case JsError(a) => BadRequest(a.toString())
    }
  }

  def getUsers() = Action {
    Ok(Json.toJson(userService.getAllUsers()))
  }

  def getUserByName(name: String) = Action {

    userService.getUserByUserName(name) match {
      case Some(a: User) => Ok(Json.toJson(a))
      case _             => NotFound("Not Found")
    }
  }
  def getUserByMobile(mobile: Long) = Action {

    userService.getUserByMobile(mobile) match {
      case Some(a: User) => Ok(Json.toJson(a))
      case _             => NotFound("Not Found")
    }
  }

}

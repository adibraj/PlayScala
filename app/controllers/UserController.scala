package controllers

import javax.inject.{Inject, Singleton}

import Model.User
import Model.UserValidation._
import Service.UserService
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserController @Inject()(cc: ControllerComponents)(
    userService: UserService)
    extends AbstractController(cc) {

//Mobile should be of 10Digits
  def registerUser() = Action(parse.json).async { request =>
    Future {
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
  }

  def getUsers() = Action.async {
    Future { Ok(Json.toJson(userService.getAllUsers())) }
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

  def editUser(id: Int) = Action(parse.json[User]) { request =>
    val user = request.body
    userService.editUser(id, user) match {
      case Some(a) => Ok(Json.toJson(a))
      case _       => BadRequest("User Id Invalid")
    }
  }

}

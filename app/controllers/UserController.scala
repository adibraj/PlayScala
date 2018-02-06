package controllers

import javax.inject.{Inject, Singleton}

import Model.User
import Model.UserValidation._
import Service.UserService
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserController @Inject()(cc: ControllerComponents)(
    userService: UserService)
    extends AbstractController(cc) {

//Mobile should be of 10Digits
  def registerUser() = Action(parse.json[User]).async { implicit request =>
    userService
      .addUser(request.body)
      .map(i => {
        Ok(Json.toJson(i))

      })
  }

  def getUsers() = Action.async { implicit request =>
    userService.getAllUsers().map(user => Ok(Json.toJson(user)))
  }

  def getUserByName(name: String) = Action.async {

    userService.getUserByUserName(name) map (user =>
      user match {
        case Some(u) => Ok(Json.toJson(user))
        case _       => NotFound("Not Found")
      })
  }

  def getUserByMobile(mobile: Long) = Action.async {
    userService.getUserByMobile(mobile) map (user =>
      user match {
        case Some(u) => Ok(Json.toJson(user))
        case _       => NotFound("Not Found")
      })
  }

  def editUser(id: Int) = Action(parse.json[User]).async { request =>
    userService.editUser(id, request.body) map { res =>
      {
        if (res == 1)
          Ok("Record Updated")
        else
          NotFound("Record With id not found")
      }

    }
  }

}

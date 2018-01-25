package controllers

import javax.inject.{Inject, Singleton}

import Service.UserService
import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class UserController @Inject()(cc: ControllerComponents)(
    userService: UserService)
    extends AbstractController(cc) {

//Mobile should be of 10Digits
  def registerUser() = Action { request =>
    val asd = userService.addUser(request.body)
    if (asd) {
      Ok("User Added Succesfull")
    } else
      BadRequest("User not Added")
  }

  def getUsers() = Action {

    Ok(userService.getAllUsers())
  }

  def getUserByName(name: String) = Action {

    userService.getUserByUserName(name) match {
      case Some(a: JsValue) => Ok(a)
      case _                => BadRequest("Not Found")
    }
  }
  def getUserByMobile(mobile: Long) = Action {

    userService.getUserByMobile(mobile) match {
      case Some(a: JsValue) => Ok(a)
      case _                => BadRequest("Not Found")
    }
  }

}

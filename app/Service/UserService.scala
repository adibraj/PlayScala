package Service

import Model.User

import scala.collection.mutable.ArrayBuffer

class UserService {
  var listUser: ArrayBuffer[User] = new ArrayBuffer[User]()

  def addUser(user: User): Option[User] = {
    listUser += (user)
    Some(user)
  }

  def getAllUsers(): List[User] = {
    listUser.toList
  }

  def getUserByUserName(name: String): Option[User] = {

    val userFound = listUser.find(u => u.firstName.equals(name))
    userFound
  }

  def getUserByMobile(mobile: Long): Option[User] = {
    val userFound = listUser.find(u => u.mobile.equals(mobile))
    userFound

  }

}

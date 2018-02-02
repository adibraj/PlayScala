package Service

import javax.inject.Inject

import Model.User
import play.api.Logger
import play.api.db._

import scala.collection.mutable.ArrayBuffer

class UserService @Inject()(dbApi: DBApi) {
  var listUser: ArrayBuffer[User] = new ArrayBuffer[User]()
  val db = dbApi.database("default")

  def addUser(user: User): Option[User] = {
    db.withConnection(implicit connection => {
      val st = connection.prepareStatement(
        "insert into user (first_name,last_name,mobile) values( ?,?,?)")
      st.setString(1, user.firstName)
      st.setString(2, user.lastName)
      st.setLong(3, user.mobile)
      val insert = st.executeUpdate()
      Logger.debug(insert.toString)
      if (insert == 1)
        return Some(user)
      else
        None
    })
  }

  def getAllUsers(): List[User] = {
    var listUser: ArrayBuffer[User] = new ArrayBuffer[User]()
    db.withConnection(connection => {
      val st = connection.prepareStatement("select * from user")
      val result = st.executeQuery()
      while (result.next()) {
        val user = User(Some(result.getInt(1)),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getLong("mobile"))
        listUser += user
      }
    })
    listUser.toList
  }

  def getUserByUserName(name: String): Option[User] = {
    db.withConnection(connection => {
      val st =
        connection.prepareStatement("select * from user where first_name=?")
      st.setString(1, name)
      val result = st.executeQuery()
      if (result.first())
        return Some(
          User(Some(result.getInt(1)),
               result.getString("first_name"),
               result.getString("last_name"),
               result.getLong("mobile")))
      None
    })
  }

  def getUserByMobile(mobile: Long): Option[User] = {
    db.withConnection(connection => {
      val st = connection.prepareStatement("select * from user where mobile=?")
      st.setLong(1, mobile)
      val result = st.executeQuery()
      if (result.first())
        return Some(
          User(Some(result.getInt(1)),
               result.getString("first_name"),
               result.getString("last_name"),
               result.getLong("mobile")))
      None
    })
  }

  def editUser(id: Int, user: User): Option[User] = {
    db.withConnection(connection => {
      val st = connection.prepareStatement("select * from user where id=?")
      st.setInt(1, id);
      val result = st.executeQuery()
      if (result.first()) {
        val stm = connection.prepareStatement(
          "update user set first_name=?, last_name=?,mobile=? where id=?")
        stm.setInt(4, id);
        stm.setString(1, user.firstName)
        stm.setString(2, user.lastName)
        stm.setLong(3, user.mobile)
        val result = stm.executeUpdate()
        if (result == 1)
          return Some(user)
        None
      }
      None
    })
  }

}

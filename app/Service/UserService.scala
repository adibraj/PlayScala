package Service

import javax.inject.Inject

import Model.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

class UserService @Inject()(val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  class UserTableDef(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")
    def mobile = column[Long]("mobile")

    override def * =
      (id, firstName, lastName, mobile) <> (User.tupled, User.unapply)
  }
  val users = lifted.TableQuery[UserTableDef]
  var listUser: ArrayBuffer[User] = new ArrayBuffer[User]()

  def addUser(user: User): Future[User] = {
    db.run(
      (users returning users
        .map(_.id) into ((user, id) => user.copy(id = id))) += user)
  }

  def getAllUsers(): Future[Seq[User]] = {
    db.run(users.result)
  }

  def getUserByUserName(name: String): Future[Option[User]] = {
    val user = users.filter(_.firstName === (name))
    db.run(user.result.headOption)
  }

  def getUserByMobile(mobile: Long): Future[Option[User]] = {
    val user = users.filter(_.mobile === mobile)
    db.run(user.result.headOption)
  }

  def editUser(id: Int, user: User): Future[Int] = {
    val dbUser = for { user <- users if user.id === id } yield user
    db.run(dbUser.update(user.copy(id = Some(id))))

  }

}

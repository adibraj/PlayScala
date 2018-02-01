package controllers

import Model.User
import Model.UserValidation._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Logger
import play.api.libs.json.{JsArray, Json}
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class UserControllerTest
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {
  val user1 = User("Adib", "Rajiwate", 9665048908L)
  val user2 = User("Xyz", "Abc", 96698356L)

  "UserController Register User POST" should {
    " add the new user" in {
      val controller = inject[UserController]
      val home = controller
        .registerUser()
        .apply(
          FakeRequest(POST, "/user/add", FakeHeaders(), Json.toJson(user1)))
      Logger.debug(contentAsString(home))
      status(home) mustBe OK
      contentAsJson(home) mustBe (Json.toJson(user1))
    }

//To check All the users i have added 2 users and checked whether getAll returns the same
    "get All users " should {
      "return all the users in the database" in {
        val controller = inject[UserController]
        val add1 = controller
          .registerUser()
          .apply(
            FakeRequest(POST, "/user/add", FakeHeaders(), Json.toJson(user1)))
        val add2 = controller
          .registerUser()
          .apply(
            FakeRequest(POST, "/user/add", FakeHeaders(), Json.toJson(user2)))
        val home = controller
          .getUsers()
          .apply(FakeRequest())

        val users = contentAsJson(home).as[JsArray]
        Logger.debug("Size is :-    " + users.value.size)
        Logger.debug(contentAsString(home))
        status(home) mustBe OK
        users.value.size mustBe (2)
        contentAsJson(home) mustBe (Json.arr(Json.toJson(user1),
                                             Json.toJson(user2)))
      }
    }

    //Test Case to Check The EndPoint Returns the Correct User
    "UserController getUserByName" should {
      "Return Appropriate User with help of userName" in {
        val controller = inject[UserController]
        val home = controller
          .registerUser()
          .apply(
            FakeRequest(POST, "/user/add", FakeHeaders(), Json.toJson(user1)))
        val response = controller.getUserByName("Adib").apply(FakeRequest())
        status(response) mustBe OK

        contentAsJson(response) mustBe (Json.toJson(user1))
      }
    }
    /*"render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }*/
  }
}

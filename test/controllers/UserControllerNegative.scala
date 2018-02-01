package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
class UserControllerNegative
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {

  "UserController Register() with invalid mobile  POST" should {
    " return BadRequest as response" in {
      var user1 = Json.parse(
        """{"user": {"firstName": "Adib", "lastName": "Rajiwate", "mobile": "sadfas"}}""")
      val controller = inject[UserController]
      val home = controller
        .registerUser()
        .apply(FakeRequest(POST, "/user/add", FakeHeaders(), user1))
      status(home) mustBe BAD_REQUEST
    }
  }

  "UserController Register() with invalid user Object  POST" should {
    " return BadRequest as response" in {
      val controller = inject[UserController]
      var user1 = Json.parse(
        """{"user": {"firsasdstName": "Adib", "lastNaasdame": "Rajiwate", "mobile": 9665048908}}""")
      val home = controller
        .registerUser()
        .apply(FakeRequest(POST, "/user/add", FakeHeaders(), user1))
      status(home) mustBe BAD_REQUEST
    }
  }
  //Test Case to Check The EndPoint Returns the Correct User
  "UserController getUserByName with invalid UserName" should {
    "give NotFound error" in {
      val controller = inject[UserController]
      val response = controller.getUserByName("Adib3").apply(FakeRequest())
      status(response) mustBe NOT_FOUND
    }
  }
}

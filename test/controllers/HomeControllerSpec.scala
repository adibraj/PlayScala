package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Logger
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc.AnyContentAsJson
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerSpec
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {

  "UserController Register User POST" should {
    " add the new user" in {
      val controller = inject[UserController]

      val home = controller
        .registerUser()
        .apply(
          FakeRequest(
            POST,
            "/user/add",
            FakeHeaders(),
            AnyContentAsJson(Json.parse(
              """{"user": {"firstName": "Adib","lastName": "Rajiwate","mobile": 9665048908}}"""))))
      Logger.debug(contentAsString(home))
      status(home) mustBe OK
      contentAsString(home) mustBe ("User Added Succesfull")
    }

//To check All the users i have added 2 users and checked whether getAll returns the same
    "get All users " should {
      "return all the users in the database" in {
        val controller = inject[UserController]
        val add1 = controller
          .registerUser()
          .apply(
            FakeRequest(
              POST,
              "/user/add",
              FakeHeaders(),
              AnyContentAsJson(Json.parse(
                """{"user": {"firstName": "asd","lastName": "Rajiwate","mobile": 9665048908}}"""))))

        val add2 = controller
          .registerUser()
          .apply(
            FakeRequest(
              POST,
              "/user/add",
              FakeHeaders(),
              AnyContentAsJson(Json.parse(
                """{"user": {"firstName": "Adib","lastName": "Rajiwate","mobile": 9665048908}}"""))))

        val home = controller
          .getUsers()
          .apply(FakeRequest())

        val users = contentAsJson(home).as[JsArray]

        Logger.debug("Size is :-    " + users.value.size)
        Logger.debug(contentAsString(home))
        status(home) mustBe OK
        users.value.size mustBe (2)
        contentAsJson(home) mustBe (Json.parse(
          """[{"user": {"firstName": "asd","lastName": "Rajiwate","mobile": 9665048908}},{"user": {"firstName": "Adib","lastName": "Rajiwate","mobile": 9665048908}} ] """))
      }
    }

    //Test Case to Check The EndPoint Returns the Correct User
    "UserController getUserByName" should {
      "Return Appropriate User with help of userName" in {
        val controller = inject[UserController]
        val home = controller
          .registerUser()
          .apply(
            FakeRequest(
              POST,
              "/user/add",
              FakeHeaders(),
              AnyContentAsJson(Json.parse(
                """{"user": {"firstName": "Adib","lastName": "Rajiwate","mobile": 9665048908}}"""))))
        val user = controller.getUserByName("Adib").apply(FakeRequest())
        status(user) mustBe OK
        contentAsJson(user) mustBe (Json.parse(
          """{"user": {"firstName": "Adib","lastName": "Rajiwate","mobile": 9665048908}}"""))

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

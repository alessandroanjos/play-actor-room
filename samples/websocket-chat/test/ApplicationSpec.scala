package test

import org.specs2.mutable.Specification

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {
    "Send JavaScript content" in {
      running(FakeApplication()) {
        val js = route(FakeRequest(GET, "/assets/javascripts/chatroom.js?username=julien")).get
        status(js) must equalTo (OK)
        contentType(js) must beSome.which(_ == "text/javascript")
      }
    }
    "Resist to XSS attacks" in {
      running(FakeApplication()) {
        val js = route(FakeRequest(GET, "/assets/javascripts/chatroom.js?username='")).get
        contentAsString(js).contains("""if(data.user == '\'')""") must beTrue
      }
    }
  }

}

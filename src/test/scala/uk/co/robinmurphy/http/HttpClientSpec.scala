package uk.co.robinmurphy.http

import org.scalatest.{BeforeAndAfter, MustMatchers, FunSpec}
import scala.concurrent.duration._
import concurrent.Await
import uk.co.robinmurphy.utils.MockServer
import com.github.tomakehurst.wiremock.client.WireMock._

trait HttpClientSpec extends FunSpec with MustMatchers with BeforeAndAfter {
  var client: HttpClient = _
  def getClient: HttpClient
  val url = "http://localhost:3333"

  before {
    client = getClient
    MockServer.reset()
  }

  after {
    MockServer.stop()
  }

  describe(".get") {
    it("returns a response") {
      stubFor(get(urlEqualTo("/")).willReturn(aResponse()
        .withStatus(200).withBody("Hello world").withHeader("Content-Type", "text/html")))

      val response = client.get(url)

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("supports request headers") {
      stubFor(get(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.get(url, headers = Map("Accept" -> "application/json"))

      verify(getRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(get(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.get(url, params = Map("foo" -> "bar"))

      verify(getRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }

  describe(".post") {
    it("returns a response") {
      stubFor(post(urlEqualTo("/")).willReturn(aResponse()
        .withStatus(200).withBody("Hello world").withHeader("Content-Type", "text/html")))

      val response = client.post(url, "Body")

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("sends the request body") {
      stubFor(post(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.post(url, "Body")

      verify(postRequestedFor(urlEqualTo("/")).withRequestBody(equalTo("Body")))
    }

    it("supports request headers") {
      stubFor(post(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.post(url, "Body", headers = Map("Accept" -> "application/json"))

      verify(postRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(post(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.post(url, "Body", params = Map("foo" -> "bar"))

      verify(postRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }

  describe(".put") {
    it("returns a response") {
      stubFor(put(urlEqualTo("/")).willReturn(aResponse()
        .withStatus(200).withBody("Hello world").withHeader("Content-Type", "text/html")))

      val response =client.put(url, "Body")

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("sends the request body") {
      stubFor(put(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.put(url, "Body")

      verify(putRequestedFor(urlEqualTo("/")).withRequestBody(equalTo("Body")))
    }

    it("supports request headers") {
      stubFor(put(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.put(url, "Body", headers = Map("Accept" -> "application/json"))

      verify(putRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(put(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.put(url, "Body", params = Map("foo" -> "bar"))

      verify(putRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }

  describe(".delete") {
    it("returns a response") {
      stubFor(delete(urlEqualTo("/")).willReturn(aResponse()
        .withStatus(200).withBody("Hello world").withHeader("Content-Type", "text/html")))

      val response = client.delete(url)

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("supports request headers") {
      stubFor(delete(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.delete(url, headers = Map("Accept" -> "application/json"))

      verify(deleteRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(delete(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      client.delete(url, params = Map("foo" -> "bar"))

      verify(deleteRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }
}

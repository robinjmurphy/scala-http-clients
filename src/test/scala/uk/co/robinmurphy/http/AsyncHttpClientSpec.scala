package uk.co.robinmurphy.http

import org.scalatest.{BeforeAndAfter, MustMatchers, FunSpec}
import scala.concurrent.duration._
import concurrent.Await
import uk.co.robinmurphy.utils.MockServer
import com.github.tomakehurst.wiremock.client.WireMock._

trait AsyncHttpClientSpec extends FunSpec with MustMatchers with BeforeAndAfter {
  var client: AsyncHttpClient = _
  def getClient: AsyncHttpClient
  val url = "http://localhost:3333"
  val timeout = 2.seconds

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

      val request = client.get(url)
      val response = Await.result(request, timeout)

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("supports request headers") {
      stubFor(get(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.get(url, headers = Map("Accept" -> "application/json"))
      Await.ready(request, timeout)

      verify(getRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(get(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.get(url, params = Map("foo" -> "bar"))
      Await.ready(request, timeout)

      verify(getRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }

  describe(".post") {
    it("returns a response") {
      stubFor(post(urlEqualTo("/")).willReturn(aResponse()
        .withStatus(200).withBody("Hello world").withHeader("Content-Type", "text/html")))

      val request = client.post(url, "Body")
      val response = Await.result(request, timeout)

      response.statusCode must be(200)
      response.body must be("Hello world")
      response.headers.get("Content-Type") must be(Some("text/html"))
    }

    it("sends the request body") {
      stubFor(get(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.get(url)
      Await.ready(request, timeout)

      verify(postRequestedFor(urlEqualTo("/")).withRequestBody(equalTo("Body")))
    }

    it("supports request headers") {
      stubFor(post(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.post(url, "Body", headers = Map("Accept" -> "application/json"))
      Await.ready(request, timeout)

      verify(postRequestedFor(urlEqualTo("/")).withHeader("Accept", matching("application/json")))
    }

    it("support query string parameters") {
      stubFor(post(urlEqualTo("/?foo=bar")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.post(url, "Body", params = Map("foo" -> "bar"))
      Await.ready(request, timeout)

      verify(postRequestedFor(urlEqualTo("/?foo=bar")))
    }
  }
}

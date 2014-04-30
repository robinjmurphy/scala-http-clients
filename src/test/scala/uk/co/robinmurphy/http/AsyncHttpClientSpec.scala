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
      stubFor(get(urlEqualTo("/")).willReturn(aResponse().withStatus(200).withBody("Hello world")))

      val request = client.get(url)
      val response = Await.result(request, timeout)

      response.statusCode must be(200)
      response.body must be("Hello world")
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
}

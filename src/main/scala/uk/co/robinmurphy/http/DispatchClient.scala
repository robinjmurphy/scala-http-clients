package uk.co.robinmurphy.http

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import dispatch._
import dispatch.{url => Url}

class DispatchClient extends AsyncHttpClient {
  def get(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url) <:< headers).map(response => Response(response.getStatusCode, response.getResponseBody))
  }

  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url)).map(response => Response(response.getStatusCode, response.getResponseBody))
  }

  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url)).map(response => Response(response.getStatusCode, response.getResponseBody))
  }

  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url)).map(response => Response(response.getStatusCode, response.getResponseBody))
  }
}

package uk.co.robinmurphy.http

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import dispatch._
import dispatch.{url => Url}
import com.ning.http.client.FluentCaseInsensitiveStringsMap
import scala.collection.JavaConverters._

class DispatchClient extends AsyncHttpClient {

  private def headersFromDispatchHeaders(headers: FluentCaseInsensitiveStringsMap): Map[String, String] = {
    headers.entrySet.asScala.toList
      .foldLeft(Map[String, String]())((map, header) => map ++ Map(header.getKey -> header.getValue.asScala.mkString(", ")))
  }

  def get(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url) <:< headers <<? params).map { response =>
      Response(response.getStatusCode, response.getResponseBody, headersFromDispatchHeaders(response.getHeaders))
    }
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

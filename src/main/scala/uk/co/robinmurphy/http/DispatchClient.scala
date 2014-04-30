package uk.co.robinmurphy.http

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import dispatch._
import dispatch.{url => Url}
import com.ning.http.client.FluentCaseInsensitiveStringsMap
import scala.collection.JavaConverters._

class DispatchClient extends AsyncHttpClient {

  def get(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url).GET <:< headers <<? params).map(responseFromDispatchResponse)
  }

  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url).PUT <:< headers <<? params).map(responseFromDispatchResponse)
  }

  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url).POST <:< headers <<? params << body).map(responseFromDispatchResponse)
  }

  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    Http(Url(url).DELETE <:< headers <<? params).map(responseFromDispatchResponse)
  }

  private def headersFromDispatchHeaders(headers: FluentCaseInsensitiveStringsMap): Map[String, String] = {
    headers.entrySet.asScala.toList
      .foldLeft(Map[String, String]())((map, header) => map ++ Map(header.getKey -> header.getValue.asScala.mkString(", ")))
  }

  private def responseFromDispatchResponse(response: dispatch.Res): Response = {
    Response(response.getStatusCode, response.getResponseBody, headersFromDispatchHeaders(response.getHeaders))
  }
}

package uk.co.robinmurphy.http

import concurrent.Future
import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem
import spray.http.HttpHeaders.RawHeader

class SprayHttpClient extends AsyncHttpClient {
  implicit val system = ActorSystem()
  import system.dispatcher
  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

  def sprayHeadersFromMap(map: Map[String, String]): List[HttpHeader] = {
    map.foldLeft(List[HttpHeader]())((list, header) => list ++ List(RawHeader(header._1, header._2)))
  }

  def get(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    pipeline(Get(uri(url, params)).withHeaders(sprayHeadersFromMap(headers))).map(responseFromSprayResponse)
  }

  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    pipeline(Put(uri(url, params)).withHeaders(sprayHeadersFromMap(headers))
      .withEntity(HttpEntity(body))).map(responseFromSprayResponse)
  }

  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    pipeline(Post(uri(url, params)).withHeaders(sprayHeadersFromMap(headers))
      .withEntity(HttpEntity(body))).map(responseFromSprayResponse)
  }

  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response] = {
    pipeline(Delete(uri(url, params)).withHeaders(sprayHeadersFromMap(headers))).map(responseFromSprayResponse)
  }

  private def uri(url: String, params: Map[String, String]): Uri = Uri(url).copy(query = Uri.Query(params))

  private def responseFromSprayResponse(response: HttpResponse): Response = {
    Response(response.status.intValue, response.entity.asString, headersFromSprayHeaders(response.headers))
  }

  private def headersFromSprayHeaders(headers: List[HttpHeader]): Map[String, String] = {
    headers.foldLeft(Map[String, String]())((map, header) => map ++ Map(header.name -> header.value))
  }
}

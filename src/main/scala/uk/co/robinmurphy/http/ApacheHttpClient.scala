package uk.co.robinmurphy.http

import org.apache.http.client.fluent.Request
import org.apache.commons.io.IOUtils
import org.apache.http.{HttpResponse, Header}
import org.apache.http.message.BasicHeader
import org.apache.http.entity.ContentType

class ApacheHttpClient extends HttpClient {

  def get(url: String, params: Map[String, String], headers: Map[String, String]): Response = {
    val response = Request.Get(url + queryStringFromParams(params))
      .setHeaders(apacheHeadersFromMap(headers):_*)
      .execute().returnResponse()
    responseFromApacheResponse(response)
  }

  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response = {
    val response = Request.Put(url + queryStringFromParams(params))
      .bodyStream(IOUtils.toInputStream(body))
      .setHeaders(apacheHeadersFromMap(headers):_*)
      .execute().returnResponse()
    responseFromApacheResponse(response)
  }

  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response = {
    val response = Request.Post(url + queryStringFromParams(params))
      .bodyStream(IOUtils.toInputStream(body))
      .setHeaders(apacheHeadersFromMap(headers):_*)
      .execute().returnResponse()
    responseFromApacheResponse(response)
  }

  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Response = {
    val response = Request.Delete(url + queryStringFromParams(params))
      .setHeaders(apacheHeadersFromMap(headers):_*)
      .execute().returnResponse()
    responseFromApacheResponse(response)
  }

  private def responseFromApacheResponse(response: HttpResponse): Response = {
    Response(response.getStatusLine.getStatusCode, IOUtils.toString(response.getEntity.getContent), headersFromApacheHeaders(response.getAllHeaders))
  }

  private def apacheHeadersFromMap(map: Map[String, String]): List[Header] = {
    map.foldLeft(List[Header]())((list, mapEntry) => list ++ List(new BasicHeader(mapEntry._1, mapEntry._2)))
  }

  private def headersFromApacheHeaders(headers: Array[Header]): Map[String, String] = {
    headers.toList.foldLeft(Map[String, String]())((map, header) => map ++ Map(header.getName -> header.getValue))
  }

  private def queryStringFromParams(params: Map[String, String]): String = {
    val pairs = params.toList.map(pair => s"${pair._1}=${pair._2}")
    if (pairs.isEmpty) "" else pairs.mkString("?", "&", "")
  }

}

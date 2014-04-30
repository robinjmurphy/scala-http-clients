package uk.co.robinmurphy.http

import concurrent.Future

trait AsyncHttpClient {
  def get(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response]
  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response]
  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Future[Response]
  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Future[Response]
}

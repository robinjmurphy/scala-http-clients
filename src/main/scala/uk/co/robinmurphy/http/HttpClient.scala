package uk.co.robinmurphy.http

trait HttpClient {
  def get(url: String, params: Map[String, String], headers: Map[String, String]): Response
  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response
  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response
  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Response
}

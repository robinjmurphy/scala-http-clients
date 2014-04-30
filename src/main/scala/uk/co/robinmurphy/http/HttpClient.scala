package uk.co.robinmurphy.http

trait HttpClient {
  def get(url: String, params: Map[String, String] = Map.empty, headers: Map[String, String] = Map.empty): Response
  def put(url: String, body: String, params: Map[String, String] = Map.empty, headers: Map[String, String] = Map.empty): Response
  def post(url: String, body: String, params: Map[String, String] = Map.empty, headers: Map[String, String] = Map.empty): Response
  def delete(url: String, params: Map[String, String] = Map.empty, headers: Map[String, String] = Map.empty): Response
}

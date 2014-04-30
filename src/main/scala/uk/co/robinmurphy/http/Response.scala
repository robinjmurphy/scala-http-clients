package uk.co.robinmurphy.http

case class Response(statusCode: Int, body: String, headers: Map[String, String] = Map.empty)

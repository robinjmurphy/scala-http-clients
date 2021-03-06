# scala-http-clients

[![Build Status](https://travis-ci.org/robinjmurphy/scala-http-clients.svg)](https://travis-ci.org/robinjmurphy/scala-http-clients)

> HTTP client examples in Scala

When learning Scala I found very few good examples of HTTP clients in use. This project contains a set of example HTTP clients that use a range of popular Scala and Java libraries.

## Features

All of the example clients support:

* Request headers and query string paramaters passed in as `Map`'s
* `GET`, `POST`, `PUT` and `DELETE` operations

## Interface

The same interface is implemented by all of the example clients:

```scala
trait HttpClient {
  def get(url: String, params: Map[String, String], headers: Map[String, String]): Response
  def put(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response
  def post(url: String, body: String, params: Map[String, String], headers: Map[String, String]): Response
  def delete(url: String, params: Map[String, String], headers: Map[String, String]): Response
}
```

where the `Response` object is a case class:

```scala
case class Response(statusCode: Int, body: String, headers: Map[String, String])
```

An asynchronous version of the interface is implemented where asyncronous clients are used. The asynchronous return type is `Future[Response]`.

## Clients

|Client|Library|Sync/Async|
|------|-------|----------|
|[DispatchHttpClient](src/main/scala/uk/co/robinmurphy/http/DispatchHttpClient.scala)|[Dispatch](http://dispatch.databinder.net/Dispatch.html)|Async|
|[ApacheHttpClient](src/main/scala/uk/co/robinmurphy/http/ApacheHttpClient.scala)|[Apache HttpComponents Client](https://hc.apache.org/httpcomponents-client-ga/index.html)|Sync|
|[SprayHttpClient](src/main/scala/uk/co/robinmurphy/http/SprayHttpClient.scala)|[Sprary client](http://spray.io/documentation/1.2.1/spray-client/)|Async|

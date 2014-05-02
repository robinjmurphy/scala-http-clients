package uk.co.robinmurphy.http

class ApacheHttpClientSpec extends HttpClientSpec {
  def getClient: HttpClient = new ApacheHttpClient
}

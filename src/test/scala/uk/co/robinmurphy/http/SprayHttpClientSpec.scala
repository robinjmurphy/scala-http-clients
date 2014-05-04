package uk.co.robinmurphy.http

class SprayHttpClientSpec extends AsyncHttpClientSpec {
  def getClient: AsyncHttpClient = {
    new SprayHttpClient
  }
}

package uk.co.robinmurphy.http

class DispatchClientSpec extends AsyncHttpClientSpec {
  override def getClient: AsyncHttpClient = {
    new DispatchClient
  }
}

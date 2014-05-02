package uk.co.robinmurphy.http

class DispatchHttpClientSpec extends AsyncHttpClientSpec {
  override def getClient: AsyncHttpClient = {
    new DispatchHttpClient
  }
}

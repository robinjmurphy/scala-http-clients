package uk.co.robinmurphy.http

class DispatchClientSpec extends AsyncHttpClientSpec {
  override val name: String = "DispatchClient"

  override def getClient: AsyncHttpClient = {
    new DispatchClient
  }
}

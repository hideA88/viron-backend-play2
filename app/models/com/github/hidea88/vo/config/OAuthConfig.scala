package models.com.github.hidea88.vo.config

//TODO あとでなおす
case class OAuthConfig(authUri: String,
  clientId: String,
  clientSecret: String,
  tokenEndpoint:String,
  scope: String,
  responseType: String,
  redirectUri: String,
  oauthGrantType: String,
  cryotoSecret: String,
  requestTimeout: Int
)

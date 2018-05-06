package models.viron.vo

case class VironAuth(`type`: AuthType, provider: AuthProvider, url: AuthUrl, method: HttpMethod)

object VironAuth {

  object Const{
    val GOOGLE_AUTH = VironAuth(AuthType.OAuth,   AuthProvider("google"), AuthUrl("/googlesignin"), HttpMethod.Post)
    val SIGN_OUT    = VironAuth(AuthType.SignOut, AuthProvider(""),       AuthUrl("/signout"),      HttpMethod.Post)
  }
}

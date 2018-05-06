package models.viron.vo

import models.base.StringEnumLike

sealed abstract class AuthType(override val value: String) extends StringEnumLike

object AuthType{
  case object Email   extends AuthType("email")
  case object OAuth   extends AuthType("oauth")
  case object SignOut extends AuthType("signout")
}

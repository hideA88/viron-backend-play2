package models.viron.vo

import models.base.StringEnumLike

sealed abstract class HttpMethod(override val value: String) extends StringEnumLike

object HttpMethod {
  case object Get    extends HttpMethod("get")
  case object Post   extends HttpMethod("post")
  case object Put    extends HttpMethod("put")
  case object Delete extends HttpMethod("delete")
}

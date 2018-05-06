package models.com.github.hidea88.vo.config

import scala.concurrent.duration.FiniteDuration

case class JwtConfig (
  jwtHeaderName: String,
  duration: FiniteDuration,
  key: String
)

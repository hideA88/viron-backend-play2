package controllers.builders

import jp.t2v.lab.play2.auth.{AuthenticityToken, TokenAccessor}
import models.com.github.hidea88.vo.config.JwtConfig
import play.api.mvc.{RequestHeader, Result}
import utils.LoggerProvider

class JwtTokenAccessor(jwtConfig: JwtConfig) extends TokenAccessor with LoggerProvider{
  override def extract(request: RequestHeader): Option[AuthenticityToken] = {
    logger.info("call jwt token accessor")
    logger.info(request.headers.toString())
    //TODO implement googleからくるtokenを外す部分もこいつがしないといけないのでは？
    logger.info(request.toString())

    request.headers.get(jwtConfig.jwtHeaderName)
  }

  override def put(token: AuthenticityToken)(result: Result)(implicit request: RequestHeader): Result = {
    result.withHeaders(jwtConfig.jwtHeaderName -> token)
  }

  override def delete(result: Result)(implicit request: RequestHeader): Result = result
}

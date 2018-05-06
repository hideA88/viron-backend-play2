package controllers.builders

import jp.t2v.lab.play2.auth.{AsyncIdContainer, AuthenticityToken}
import models.com.github.hidea88.service.LoginService
import models.com.github.hidea88.vo.config.JwtConfig
import models.com.github.hidea88.vo.user.AdminUserId
import pdi.jwt.{Jwt, JwtAlgorithm}
import play.api.libs.json.{JsValue, Json, Reads}
import play.api.mvc.RequestHeader
import utils.LoggerProvider

import scala.concurrent.{ExecutionContext, Future}

class JwtIdContainer(jwtConfig: JwtConfig, loginService: LoginService) extends AsyncIdContainer[AdminUserId] with LoggerProvider{
  override def startNewSession(userId: AdminUserId, timeoutInSeconds: Int)(implicit request: RequestHeader, context: ExecutionContext): Future[AuthenticityToken] = {
    logger.info("start new session")
    val token = loginService.generateJwtToken(userId)
    //TODO implement tokenの履歴管理
    Future.successful(token)
  }

  //TODO implement DBからtokenを除去する実装？
  override def remove(token: AuthenticityToken)(implicit context: ExecutionContext): Future[Unit] = Future.successful(())

  case class JwtPayload(aud: Seq[String], exp: Long, iat: Long, jti: String)
  implicit lazy val jwtPayloadReads: Reads[JwtPayload] = Json.reads[JwtPayload]

  override def get(token: AuthenticityToken)(implicit context: ExecutionContext): Future[Option[AdminUserId]] = {
    logger.info("call jwtId container get method")
    logger.info(s"token: ${token}")
    val id = for {
      decodedJwt <- Jwt.decode(token, jwtConfig.key, algorithms = Seq(JwtAlgorithm.HS512)).toOption
      jwtPayload <- Json.parse(decodedJwt).asOpt[JwtPayload]
      id         <- jwtPayload.aud.headOption
    } yield AdminUserId(id)

    Future.successful(id)
  }

  override def prolongTimeout(token: AuthenticityToken, timeoutInSeconds: Int)(implicit request: RequestHeader, context: ExecutionContext): Future[Unit] = {
    //TODO implement ここでsessionの更新をする
    logger.info("prorong Time out")
    Future.successful(())
  }
}

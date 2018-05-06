package models.com.github.hidea88.service

import java.time.ZonedDateTime
import java.util.UUID

import jp.t2v.lab.play2.auth.AuthenticityToken
import models.ConsoleContext
import models.base.FutureEither
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.vo.config.{JwtConfig, OAuthConfig}
import models.com.github.hidea88.vo.user.AdminUserId
import org.apache.commons.codec.binary.Base64
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import utils.LoggerProvider

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class LoginService(
  config: OAuthConfig,
  jwtConfig: JwtConfig,
  wSClient: WSClient
) extends LoggerProvider{

  def generateJwtToken(userId: AdminUserId): AuthenticityToken = {
    //TODO AuthenticaityTokenの型はいい感じにしたほうがいいかもしれない
    val expiresAt =ZonedDateTime.now().plusSeconds(jwtConfig.duration.toSeconds).toEpochSecond
    val claim = JwtClaim()
      .to(userId.value.toString)
      .issuedNow
      .expiresAt(expiresAt)
      .withId(UUID.randomUUID().toString)
    val token = Jwt.encode(claim, jwtConfig.key, JwtAlgorithm.HS512)
    token
  }

  def existsUser(email: String)(implicit ctx: ConsoleContext): FutureEither[String, AdminUser] = {
    //TODO implement DBからIDを引っ張ってくる
    logger.info(s"email: ${email}")
    FutureEither.right(AdminUser(AdminUserId("111111"), "nameA", email))
  }

  def auth(requestParam: Map[String, Seq[String]], sessionStateOpt: Option[String])(implicit ctx: ConsoleContext) : FutureEither[String, (AdminUser, String)] = {
    implicit val ec = ctx.executionContext
    for {
      sessionState <- FutureEither(sessionStateOpt.toRight("empty session state"))
      _            <- confirmState(requestParam, sessionState)
      code         <- FutureEither(requestParam.get("code").flatMap(_.headOption).toRight("not has code"))
      idToken      <- exchangeCodeForToken(code)
      email        <- obtainEmail(idToken)
      user         <- existsUser(email)
    } yield (user, sessionState)
  }

  private def confirmState(requestParam: Map[String, Seq[String]], sessionState: String): FutureEither[String, Boolean] = {
    {
      for {
        paramState   <- requestParam.get("state").flatMap(_.headOption)
        sessionState <- Some(sessionState)
      } yield paramState == sessionState
    } match {
      case Some(s) => FutureEither.right(s)
      case None    => FutureEither.left("error")
    }
  }

  //TODO FIXME クローズ処理をしなくてよい?
  private def exchangeCodeForToken(code: String)(implicit ctx: ConsoleContext): FutureEither[String, String] = {
    implicit val ec = ctx.executionContext
    val request =
      wSClient
        .url(config.tokenEndpoint)
        .withHttpHeaders("Accept" -> "application/json")
        .withRequestTimeout(config.requestTimeout.millis)

    val body = Map(
      "code" -> Seq(code),
      "client_id" -> Seq(config.clientId),
      "client_secret" -> Seq(config.clientSecret),
      "redirect_uri" -> Seq(config.redirectUri),
      "grant_type" -> Seq(config.oauthGrantType)
    )

    val v = request.post(body).map{response =>
      val token = (response.json \ "id_token").as[String]
      Right(token)
    }.recover{case e => Left(e.toString)}
    FutureEither(v)
  }

  private def obtainEmail(idToken: String)(implicit ctx: ConsoleContext): FutureEither[String, String] = {
    implicit val ec = ctx.executionContext
    val v = Future {
      val tokenSegment = idToken.split("\\.")
      val base64EncodedClaims = tokenSegment(1)
      val claims = new String(Base64.decodeBase64(base64EncodedClaims))
      val json = Json.parse(claims)
      (json \ "email").asOpt[String] match {
        case Some(email) => Right(email)
        case None        => Left("not obtain")
      }
    }
    FutureEither(v)
  }

  //TODO implement Urlクラスでの実装
  def generateRedirectApiUrl(state: String) = {
    s"${config.authUri}?" +
      s"client_id=${config.clientId}" +
      s"&response_type=${config.responseType}" +
      s"&scope=${config.scope}" +
      s"&redirect_uri=${config.redirectUri}" +
      s"&state=${config.redirectUri}"
  }

}

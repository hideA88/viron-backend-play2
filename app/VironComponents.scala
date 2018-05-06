package models.hidea88.viron.app

import com.softwaremill.macwire.wire
import com.typesafe.config.Config
import controllers._
import controllers.builders.{AuthConfigImpl, Authority, JwtIdContainer, JwtTokenAccessor}
import jp.t2v.lab.play2.auth._
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.service.{LoginService, VironServiceImpl}
import models.com.github.hidea88.vo.config.{JwtConfig, OAuthConfig}
import models.com.github.hidea88.vo.user.AdminUserId
import models.viron.service._
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.filters.cors.CORSComponents
import router.Routes
import scalikejdbc.PlayInitializer

import scala.concurrent.duration.{Duration, FiniteDuration}

class VironComponents(context: Context) extends BuiltInComponentsFromContext(context) with HttpFiltersComponents with CORSComponents with AssetsComponents with AhcWSComponents {
  override lazy val httpErrorHandler = new AppErrorHandler()
  override def httpFilters: Seq[EssentialFilter] = super.httpFilters ++ Seq(corsFilter)

  lazy val router: Router = {
    val prefix: String = httpConfiguration.context
    wire[Routes]
  }

  /*auth*/
  protected[this] lazy val authConfig:     AuthConfig[AdminUserId, AdminUser, Authority]     = wire[AuthConfigImpl]
  protected[this] lazy val idContainer:    AsyncIdContainer[AdminUserId]                     = wire[JwtIdContainer]
  protected[this] lazy val tokenAccessor:  TokenAccessor                                     = wire[JwtTokenAccessor]
  protected[this] lazy val authComponents: AuthComponents[AdminUserId, AdminUser, Authority] = wire[DefaultAuthComponents[AdminUserId, AdminUser, Authority]]


  /*controller*/
  protected[this] lazy val vironController: VironController = wire[VironController]
  protected[this] lazy val loginController: LoginController = wire[LoginController]

  /*service*/
  protected[this] lazy val vironService:    VironService    = wire[VironServiceImpl]
  protected[this] lazy val loginService:    LoginService    = wire[LoginService]

  /*config*/
  private[this]   lazy val oauthRawConfig:  Config          = configuration.get[Config]("authentication.google.oauth")
  protected[this] lazy val oauthConfig:     OAuthConfig     = OAuthConfig(
    oauthRawConfig.getString("auth.uri"),
    oauthRawConfig.getString("client.id"),
    oauthRawConfig.getString("client.secret"),
    oauthRawConfig.getString("token.endpoint"),
    oauthRawConfig.getString("scope"),
    oauthRawConfig.getString("response.type"),
    oauthRawConfig.getString("redirect.url"),
    oauthRawConfig.getString("grant.type"),
    oauthRawConfig.getString("crypto.secret"),
    oauthRawConfig.getInt("request.timeout")
  )

  private[this]   lazy val jwtRawConfig:   Config          = configuration.get[Config]("authentication.jwt.authenticator")
  protected[this] lazy val jwtConfig:      JwtConfig       = {
    JwtConfig(
      jwtRawConfig.getString("headerName"),
      Duration.apply(
        jwtRawConfig.getString("authenticatorExpiry")
      ).asInstanceOf[FiniteDuration],
      jwtRawConfig.getString("crypter.key")
    )
  }


  /* ScalikeJDBC */
  //wire[PlayInitializer]


}

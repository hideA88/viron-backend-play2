package controllers

import controllers.builders.Authority
import jp.t2v.lab.play2.auth.AuthComponents
import models.ConsoleContext
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.service.LoginService
import models.com.github.hidea88.vo.user.AdminUserId
import play.api.mvc.ControllerComponents
import utils.LoggerProvider

import scala.concurrent.{ExecutionContext, Future}

class LoginController(
  cc: ControllerComponents,
  val auth: AuthComponents[AdminUserId, AdminUser, Authority],
  loginService: LoginService
)(implicit ec: ExecutionContext) extends BaseController(cc, auth) with LoggerProvider {

  def redirectOAuthPage() = Action.async { request =>
    logger.info("call redirect oauth page")
    Future {
      val redirectPageUri = request.getQueryString("redirect_url").getOrElse("") //TODO default page url
      val state = redirectPageUri
      val requestApiUrl = loginService.generateRedirectApiUrl(state)
      Found(requestApiUrl).withSession("state" -> state)
    }
  }

  def oauth() = Action.async {implicit request =>
    logger.info("call oauth api")
    logger.info(request.toString())
    ConsoleContext.within{implicit ctx =>
      val requestParam = request.queryString
      val sessionState = request.session.get("state")
      loginService.auth(requestParam, sessionState)
    }.foldM(
      {s => Future.successful(toOkJson(s))},
      { case (user, redirectUrl) =>
        auth.idContainer.startNewSession(user.id, auth.authConfig.sessionTimeoutInSeconds)
          .map { token =>
            auth.tokenAccessor.put(token)(Found(s"${redirectUrl}?token=${token}")) //FIXME もうちょっと丁寧にURLを作った方がよさそう
          }
      }
    )
  }

  def signout() = Action { implicit request =>
    logger.info("call logout api")
    //TODO implement logoutの実装
    toOkJson("logout")
  }
}

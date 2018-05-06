package controllers

import controllers.builders.Authority
import controllers.builders.UserRole._
import jp.t2v.lab.play2.auth.AuthComponents
import json.VironJsonSupport
import models.ConsoleContext
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.vo.user.AdminUserId
import models.viron.service.VironService
import play.api.mvc._
import utils.LoggerProvider

import scala.concurrent.ExecutionContext
class VironController(
  cc: ControllerComponents,
  val auth: AuthComponents[AdminUserId, AdminUser, Authority],
  vironService: VironService
)(implicit ec: ExecutionContext)extends BaseController(cc, auth) with LoggerProvider with VironJsonSupport {

  def getVironAuthType() = Action {implicit request =>
    logger.info("call viron auth type api")
    val authType = vironService.getVironAuthType
    toOkJson(authType)
  }

  def getSwaggerJson() = OAuthAction(Authority.userRole(Admin, Viewer)).async { implicit request =>
    logger.info("call swagger json")
    ConsoleContext.within{implicit ctx =>
      vironService.getSwagger
    }.fold(
      toInternalServerError(_), toOkJson(_)
    )
  }

  def getViron() =  OAuthAction(Authority.userRole(Admin, Viewer)).async { implicit request =>
    logger.info("call global menu")
    ConsoleContext.within { implicit ctx =>
      vironService.getGlobalMenu
    }.fold(
      _ => toInternalServerError("aaa"), toOkJson(_) //TODO Implement 例外処理
    )
  }

}

package controllers

import controllers.builders.{Authority, OAuthActionBuilders}
import play.api.libs.json.{JsPath, JsonValidationError, Reads}
import play.api.libs.json._
import play.api.mvc._
import jp.t2v.lab.play2.auth.AuthComponents
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.vo.user.AdminUserId

import scala.concurrent.Future

abstract class BaseController(
  cc: ControllerComponents,
  auth: AuthComponents[AdminUserId, AdminUser, Authority]
) extends AbstractController(cc) with OAuthActionBuilders {

  protected def OAuthAction(authority: Authority): ActionBuilder[AuthRequest, AnyContent] = {
    composeAuthenticationAction(Action)(cc.executionContext) andThen
      UserExistenceCheckRefiner(authority, cc.executionContext) andThen
      AuthorizationFilter(authority)(cc.executionContext)
  }


  protected def responseJsonValidationError(errs: Seq[(JsPath, Seq[JsonValidationError])]): Future[Result] = {
    Future.successful(InternalServerError(errs.toString))
  }

  protected def responseAuthError(message: String) : Future[Result]= {
    Future.successful(Unauthorized(message))
  }

  protected def toInternalServerError(message: String): Result= {
    InternalServerError(message)
  }

  protected def toOkJson[T](response: T)(implicit writes: Writes[T]) : Result= {
    Ok(Json.toJson(response))
  }



}

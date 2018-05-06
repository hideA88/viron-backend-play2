package controllers.builders

import jp.t2v.lab.play2.auth.AuthConfig
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.vo.user.AdminUserId
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc.{RequestHeader, Result}
import utils.LoggerProvider

import scala.concurrent.{ExecutionContext, Future}

class AuthConfigImpl extends AuthConfig[AdminUserId, AdminUser, Authority] with LoggerProvider{
  override def sessionTimeoutInSeconds: Int = 3600 //FIXME application.confから読み込むように

  override def resolveUser(id: AdminUserId)(implicit context: ExecutionContext): Future[Option[AdminUser]] = {
    //TODO implement DBからユーザー情報を引っ張ってくるようにする
    logger.info("id:" + id)
    val user = AdminUser(id, "name", "email")
    Future.successful(Some(user))
  }

  override def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Unauthorized("Unauthenticated"))

  override def authorizationFailed(request: RequestHeader, user: AdminUser, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Forbidden("Forbidden"))

  override def authorize(user: AdminUser, authority: Authority)(implicit context: ExecutionContext): Future[Boolean] = {
    logger.info("call auth config imple authorize")
    //TODO implement 権限チェックの実装
    Future.successful(true)
  }

}

package controllers.builders

import controllers.BaseController
import jp.t2v.lab.play2.auth.AuthActionBuilders
import models.com.github.hidea88.entity.AdminUser
import models.com.github.hidea88.vo.user.AdminUserId
import play.api.mvc.{ActionRefiner, Result}
import utils.LoggerProvider

import scala.concurrent.{ExecutionContext, Future}

trait OAuthActionBuilders extends AuthActionBuilders[AdminUserId, AdminUser, Authority] with LoggerProvider{ self: BaseController =>
  case class UserExistenceCheckRefiner(
    authority: Authority,
    override protected val executionContext: ExecutionContext
  ) extends ActionRefiner[AuthRequest, AuthRequest] {
    override protected def refine[A](request: AuthRequest[A]): Future[Either[Result, AuthRequest[A]]] = {
      implicit val ctx: ExecutionContext = executionContext
      //TODOここで実際の認証処理を記述する
      //userのemailをみて、DB内にemailがあったらOKとする。というような感じ
      logger.info("refine user:" + request.user)
      //TODO implement
      if(request.user.email.nonEmpty){
        Future.successful(Right(AuthRequest(request.user, request)))
      } else{
        Future.successful(Left(Unauthorized("hogehoge")))
      }
    }
  }
}

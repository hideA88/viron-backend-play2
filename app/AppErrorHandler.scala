package models.hidea88.viron.app


import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import utils.LoggerProvider

import scala.concurrent.Future

class AppErrorHandler extends HttpErrorHandler with LoggerProvider {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.info(s"on client error : statusCode: ${statusCode}, message:${message}, request: ${request}")
    if (statusCode == 404) {
      Future.successful(NotFound)
    } else {
      Future.successful(BadRequest)
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error(s"Internal Server Error request: ${request}", exception)
    exception match {
      case _ => Future.successful(InternalServerError)
    }
  }
}

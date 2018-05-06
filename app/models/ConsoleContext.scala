package models

import models.base.FutureEither
import scalikejdbc.{DB, DBSession}

import scala.concurrent.{ExecutionContext}

case class ConsoleContext(
  executionContext: ExecutionContext//,
  //dBsession : DBSession
)

object ConsoleContext {
  def within[L, R](f: ConsoleContext => FutureEither[L, R])(implicit ec: ExecutionContext): FutureEither[L, R] = {
    f(ConsoleContext(ec))
    /*
    DB.localTx { session =>
      f(ConsoleContext(ec,session))
    }
    */
  }
}

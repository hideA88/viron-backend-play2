package models.base

import scala.concurrent.{ExecutionContext, Future}

case class FutureEither[L, R](value: Future[Either[L, R]]){
  def map[RR](f: R => RR)(implicit ec: ExecutionContext): FutureEither[L, RR] = {
    FutureEither(value.map(_.map(f)))
  }

  def flatMap[LL >: L, RR](f: R => FutureEither[LL, RR])(implicit cc: ExecutionContext): FutureEither[LL, RR] = {
    FutureEither {
      value.flatMap {
        case Left(l) => Future.successful(Left(l))
        case Right(r) => f(r).value
      }
    }
  }

  def fold[A](whenError: L => A, whenSuccess: R => A)(implicit cc: ExecutionContext): Future[A] = {
    value.map(_.fold(whenError, whenSuccess))
  }

  def foldM[A](whenError: L => Future[A], whenSuccess: R => Future[A])(implicit ec: ExecutionContext): Future[A] = {
    value.flatMap(_.fold(whenError, whenSuccess))
  }
}

object FutureEither{
  //TODO implement
  def left[L, R](value: L): FutureEither[L, R] = FutureEither(Future.successful(Left(value)))
  def right[L, R](value: R): FutureEither[L, R] = FutureEither(Future.successful(Right(value)))

  def apply[L, R](value: Future[Either[L, R]]): FutureEither[L, R] = new FutureEither(value)
  def apply[L, R](value: Either[L, R]): FutureEither[L, R] = new FutureEither(Future.successful(value))

}

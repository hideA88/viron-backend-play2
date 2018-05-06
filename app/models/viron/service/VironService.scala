package models.viron.service

import models.viron._
import models.viron.vo.{Viron, VironAuth}
import com.iheart.playSwagger.SwaggerSpecGenerator
import models.ConsoleContext
import models.base.FutureEither
import models.viron.service.VironService.GetGlobalMenuError
import play.api.libs.json._
import utils.LoggerProvider

import scala.concurrent.Future

trait VironService extends LoggerProvider{
  implicit val cl = getClass.getClassLoader
  private val domainPackage = "models"
  private lazy val generator = SwaggerSpecGenerator(false, domainPackage)

  protected val host: String

  def getSwagger(implicit ctx: ConsoleContext) : FutureEither[String, JsValue]= {
    implicit val ec = ctx.executionContext
    val v = Future {
      generator.generate()
        .map(_ + ("host" -> JsString(host)))
        .fold(e => {
          logger.error(e.toString)
          Left("Couldn't generate swagger.")
        },
          s => Right(s)
        )
    }
    FutureEither(v)
  }

  def getVironAuthType: Seq[VironAuth]= {
    import VironAuth.Const._
    Seq(GOOGLE_AUTH, SIGN_OUT)
  }


  /**
    * globalmenueに表示するendpointの情報を記述する
    *
    * */
  def getGlobalMenu(implicit ctx: ConsoleContext): FutureEither[GetGlobalMenuError, Viron]
}

object VironService {
  trait GetGlobalMenuError
}

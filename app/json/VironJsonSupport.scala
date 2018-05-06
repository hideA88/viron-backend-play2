package json

import models.viron.vo.{Viron, VironAuth}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._

trait VironJsonSupport extends JsonSupportBase{
  /* entity writes */
  implicit val vironAuthWrites:         Writes[VironAuth]      = Json.writes[VironAuth]
  implicit val vironWrites:             Writes[Viron]          = Json.writes[Viron]

}

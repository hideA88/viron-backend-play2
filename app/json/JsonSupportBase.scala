package json

import play.api.libs.json.{JsonConfiguration, JsonNaming}

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._

import models.base._

trait JsonSupportBase {
  protected implicit val jsonConfig = JsonConfiguration(JsonNaming.Identity)

  //TODO implement enumlikeWrites


}

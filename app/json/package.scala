
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import models.viron.vo._

package object json {
  /* vo writes */
  implicit lazy val authHttpMethodWrites:    Writes[HttpMethod]     = StringWrites.contramap(_.value)
  implicit lazy val authTypeWrites:          Writes[AuthType]       = StringWrites.contramap(_.value)
  implicit lazy val authProviderWrites:      Writes[AuthProvider]   = StringWrites.contramap(_.value)
  implicit lazy val authUrlWrites:           Writes[AuthUrl]        = StringWrites.contramap(_.value)

  implicit lazy val vironThemelWrites:       Writes[VironTheme]     = StringWrites.contramap(_.value)
  implicit lazy val vironColorWrites:        Writes[VironColor]     = StringWrites.contramap(_.value)
  implicit lazy val vironNameWrites:         Writes[VironName]      = StringWrites.contramap(_.value)
  implicit lazy val vironTagWrites:          Writes[VironTag]       = StringWrites.contramap(_.value)
  implicit lazy val thumnailUrlWrites:       Writes[ThumbnailUrl]   = StringWrites.contramap(_.value)

  implicit lazy val vironPageWrites:         Writes[VironPage]      = Json.writes[VironPage]
  implicit lazy val pageSectionWrites:       Writes[PageSection]    = StringWrites.contramap(_.value)
  implicit lazy val pageGroupWrites:         Writes[PageGroup]      = StringWrites.contramap(_.value)
  implicit lazy val pageIdWrites:            Writes[PageId]         = StringWrites.contramap(_.value)
  implicit lazy val pageNameWrites:          Writes[PageName]       = StringWrites.contramap(_.value)

  implicit lazy val pageComponentWrites:     Writes[PageComponent]  = Json.writes[PageComponent]
  implicit lazy val componentApiWrites:      Writes[ComponentApi]   = Json.writes[ComponentApi]
  implicit lazy val urlPathWrites:           Writes[UrlPath]        = StringWrites.contramap(_.value)
  implicit lazy val componentNameWrites:     Writes[ComponentName]  = StringWrites.contramap(_.value)
  implicit lazy val componentStyleWrites:    Writes[ComponentStyle] = StringWrites.contramap(_.value)




}

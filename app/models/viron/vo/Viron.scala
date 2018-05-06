package models.viron.vo

case class Viron(
  theme: VironTheme,
  color: VironColor,
  name: VironName,
  tags: Seq[VironTag],
  thumbnail: ThumbnailUrl,
  pages: Seq[VironPage]
)


sealed abstract class VironColor(val value: String)
object VironColor {
  case object White extends VironColor("white")
}

case class VironName(value: String) extends AnyVal
case class VironTag(value: String) extends AnyVal
case class ThumbnailUrl(value: String) extends AnyVal //FIXME urlに変更したほうがいいかも

case class VironPage(
                      section: PageSection,
                      group: PageGroup,
                      id: PageId,
                      name: PageName,
                      components: Seq[PageComponent]
                    )

sealed abstract class PageSection(val value: String)
object PageSecition {
  case object DashBoard extends PageSection("dashboard")
  case object Manage    extends PageSection("manage")
}

case class PageGroup(value: String) extends AnyVal
case class PageId(value: String) extends AnyVal //FIXME
case class PageName(value: String) extends AnyVal

case class PageComponent( //FIXME componentStyleによって必要なフィールドが変わるので、上位traitにして切り分けた方がよさそう
                          api:   ComponentApi,
                          name:  ComponentName,
                          style: ComponentStyle
                        )
case class ComponentApi(method: HttpMethod, path: UrlPath)
case class UrlPath(value: String)       extends AnyVal
case class ComponentName(value: String) extends AnyVal

sealed abstract class ComponentStyle(val value: String)
object ComponentStyle {
  case object Number extends ComponentStyle("dashboard")
}

package models.com.github.hidea88.service

import models.ConsoleContext
import models.base.FutureEither
import models.viron.service.VironService
import models.viron.service.VironService.GetGlobalMenuError
import models.viron.vo._

class VironServiceImpl extends VironService {

  override protected val host = "localhost:9000"

  def getGlobalMenu(implicit ctx: ConsoleContext): FutureEither[GetGlobalMenuError, Viron] = {
    //TODO implement
    FutureEither.right(
      Viron(
        theme =     VironTheme.Standard,
        color =     VironColor.White,
        name  =     VironName("hideaki test"),
        tags  =     Seq(VironTag("local")),
        thumbnail = ThumbnailUrl("https://avatars3.githubusercontent.com/u/23251378?v=3&s=200"),
        pages = Seq(
          VironPage(
            section = PageSecition.DashBoard,
            group = PageGroup(""),
            id = PageId("hogehoge"),
            name = PageName("てすとびゅー"),
            components = Seq(
              PageComponent(
                api = ComponentApi(HttpMethod.Get, UrlPath("/hoge")),
                name = ComponentName("hoge call"),
                style = ComponentStyle.Number
              )
            )
          )
        )
      )
    )
  }

}

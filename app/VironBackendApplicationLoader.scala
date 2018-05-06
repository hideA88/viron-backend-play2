package models.hidea88.viron.app

import play.api.ApplicationLoader.Context
import play.api.{Application, ApplicationLoader, LoggerConfigurator}



class VironBackendApplicationLoader extends ApplicationLoader {
  override def load(context: Context): Application= {
    val v = context.environment.classLoader

    LoggerConfigurator(v).foreach(_.configure(context.environment))

    new VironComponents(context).application
  }
}



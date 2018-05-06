resolvers += "Flyway" at "https://flywaydb.org/repo"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.6")
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.6.2-PLAY2.6")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

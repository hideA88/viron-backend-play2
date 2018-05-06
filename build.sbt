name := "viron-backend"
scalaVersion := "2.12.4"
version := "0.0.1-SNAPSHOT"


resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers += "Atlassian Releases"      at "https://maven.atlassian.com/public/"
resolvers += "Sonatype OSS Snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "JcentperRepo"            at "https://jcenter.bintray.com/"

scalacOptions ++= Seq("-Ypatmat-exhaust-depth", "off")
javaOptions ++= Seq("-Xmx1G")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  "org.postgresql"           %  "postgresql"                       % "42.1.4",
  "org.scalikejdbc"          %% "scalikejdbc"                      % "3.1.0",
  "org.scalikejdbc"          %% "scalikejdbc-syntax-support-macro" % "3.1.0",
  "org.scalikejdbc"          %% "scalikejdbc-config"               % "3.1.0",
  "org.scalikejdbc"          %% "scalikejdbc-play-initializer"     % "2.6.0-scalikejdbc-3.0",
  "com.softwaremill.macwire" %% "macros"                           % "2.3.0",
  "com.pauldijou"            %% "jwt-core"                         % "0.14.0",
  "com.github.t3hnar"        %% "scala-bcrypt"                     % "3.1",
  "jp.t2v"                   %% "play2-auth"                       % "0.15.0-SNAPSHOT",
  "org.webjars"              %  "swagger-ui"                       % "3.13.6",
  "com.iheart"               %% "play-swagger"                     % "0.7.4",
  ws,
  "org.scalatestplus.play"   %% "scalatestplus-play"               % "3.1.2"            % Test,
  "jp.t2v"                   %% "play2-auth-test"                  % "0.15.0-SNAPSHOT"  % Test
)

coverageEnabled in(Test, compile) := true
coverageEnabled in(Compile, compile) := false
coverageExcludedPackages := """controllers\..*Reverse.*;router.Routes.*;"""

// TODO: remove. see https://github.com/playframework/playframework/issues/7832
dependencyOverrides ++= Set(
  "com.typesafe.akka" %% "akka-stream" % "2.5.4",
  "com.typesafe.akka" %% "akka-actor"  % "2.5.4"
)

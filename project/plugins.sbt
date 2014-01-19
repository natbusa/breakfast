scalaVersion := "2.10.2"

resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"Spray Repository"    at "http://repo.spray.io"
)

addSbtPlugin("com.typesafe.sbteclipse" %% "sbteclipse-plugin" % "2.3.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-atmos" % "0.2.0")
name := "leap"
organization in ThisBuild := "com.leap"
scalaVersion in ThisBuild := "2.13.2"

// PROJECTS

lazy val global = project
  .in(file("."))
  .settings(settings)
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    cluster,
    visualizer,
    cli
  )

lazy val cluster = project
  .settings(
    name := "cluster",
    settings,
    libraryDependencies ++= clusterDependencies
  )
  .disablePlugins(AssemblyPlugin)

lazy val visualizer = project
  .settings(
    name := "visualizer",
    settings,
    assemblySettings,
    libraryDependencies ++= clusterDependencies ++ Seq(
      dependencies.akkamgmt,
      dependencies.akkaStream,
      dependencies.akkaHttp,
      dependencies.akkaHttpCluster,
      dependencies.akkaPersistence,
      dependencies.akkaSharding,
      dependencies.akkaProdoBuf,
      dependencies.spray
    )
  )
  .dependsOn(
    cluster
  )

lazy val cli = project
  .settings(
    name := "cli",
    settings,
    assemblySettings,
    libraryDependencies ++= clusterDependencies ++ Seq(
      )
  )
  .dependsOn(
    cluster
  )

// DEPENDENCIES

lazy val dependencies =
  new {
    val logbackV                  = "1.2.3"
    val logstashV                 = "4.11"
    val scalaLoggingV             = "3.9.2"
    val slf4jV                    = "1.7.30"
    val typesafeConfigV           = "1.3.1"
    val scalatestV                = "3.1.1"
    val scalacheckV               = "1.13.5"
    val akkaVersion               = "2.6.5"
    val akkamgmtVersion           = "1.0.8"
    val akkaHttpVersion           = "10.1.12"
    val akkaHttpClusterVersion    = "1.0.8"
    val akkaPersistenceVersion    = "2.6.5"
    val akkaShardingVersion       = "2.6.5"
    val akkaProtoBufVersion       = "2.6.5"
    val sprayVersion              = "10.1.12"
    val metricsVersion            = "4.1.11"

    val logback             = "ch.qos.logback"                 % "logback-classic"               % logbackV
    val logstash            = "net.logstash.logback"           % "logstash-logback-encoder"      % logstashV
    val scalaLogging        = "com.typesafe.scala-logging"    %% "scala-logging"                 % scalaLoggingV
    val sl4j                = "org.slf4j"                      % "slf4j-log4j12"                 % slf4jV
    val sl4j_api            = "org.slf4j"                      % "slf4j-api"                     % slf4jV
    val typesafeConfig      = "com.typesafe"                   % "config"                        % typesafeConfigV
    val scalatest           = "org.scalatest"                  %% "scalatest"                    % scalatestV
    val akkaTyped           = "com.typesafe.akka"              %% "akka-actor-typed"             % akkaVersion
    val akkaCluster         = "com.typesafe.akka"              %% "akka-cluster-typed"           % akkaVersion
    val jsonSerializer      = "com.typesafe.akka"              %% "akka-serialization-jackson"   % akkaVersion
    val akkamgmt            = "com.lightbend.akka.management"  %% "akka-management"              % akkamgmtVersion
    val akkaStream          = "com.typesafe.akka"              %% "akka-stream"                  % akkaVersion
    val akkaHttp            = "com.typesafe.akka"              %% "akka-http"                    % akkaHttpVersion
    val akkaHttpCluster     = "com.lightbend.akka.management"  %% "akka-management-cluster-http" % akkaHttpClusterVersion
    val akkaPersistence     = "com.typesafe.akka"              %% "akka-persistence"             % akkaPersistenceVersion
    val akkaSharding        = "com.typesafe.akka"              %% "akka-cluster-sharding"        % akkaShardingVersion
    val akkaProdoBuf        = "com.typesafe.akka"              %% "akka-protobuf"                % akkaProtoBufVersion
    val spray               = "com.typesafe.akka"              %% "akka-http-spray-json"         % sprayVersion
    val metrics             = "io.dropwizard.metrics"           % "metrics-core"                 % metricsVersion
    val jvmMetrics          = "io.dropwizard.metrics"           % "metrics-jvm"                  % metricsVersion



  }

lazy val clusterDependencies = Seq(
  dependencies.logback,
  dependencies.logstash,
  dependencies.scalaLogging,
  dependencies.sl4j,
  dependencies.sl4j_api,
  dependencies.typesafeConfig,
  dependencies.scalatest  % "test",
  dependencies.akkaTyped,
  dependencies.akkaCluster,
  dependencies.jsonSerializer,
  dependencies.metrics,
  dependencies.jvmMetrics
)

// SETTINGS

lazy val settings =
commonSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case "application.conf"            => MergeStrategy.concat
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

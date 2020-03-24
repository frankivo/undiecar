name := "undiecar"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.2.0"

libraryDependencies += "com.google.api-client" % "google-api-client" % "1.30.9"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.30.6"
libraryDependencies += "com.google.apis" % "google-api-services-calendar" % "v3-rev411-1.25.0"

libraryDependencies += "junit" % "junit" % "4.13" % Test
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test

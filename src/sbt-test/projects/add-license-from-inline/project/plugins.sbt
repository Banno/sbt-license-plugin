addSbtPlugin("com.banno" % "sbt-license-plugin" % "latest.integration")

resolvers += Resolver.file("Local Ivy Repository", file("/home/julianpeeters/.ivy2/local/"))(Resolver.ivyStylePatterns)



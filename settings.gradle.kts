pluginManagement {
  includeBuild("build-logic")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "Stitch"

include(":app")
include(":common")
include(":codegen")
include(":ksp")
include(":gradle-plugin")
include(":gradle-plugin-api")

include(":catalog:shared")
include(":catalog:androidApp")
include(":catalog:desktopApp")
include(":catalog:webApp")

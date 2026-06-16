pluginManagement {
  includeBuild("build-logic")
  repositories {
    mavenLocal()
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
  repositories {
    mavenLocal()
    google()
    mavenCentral()
  }
}

rootProject.name = "Stitch"

include(":common")
include(":codegen")
include(":ksp")
include(":gradle-plugin")
include(":gradle-plugin-api")
include(":stitch-web")
include(":stitch-runtime")

include(":catalog:shared")
include(":catalog:androidApp")
include(":catalog:desktopApp")
include(":catalog:webApp")

import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.ktx.createVersion
import dev.teogor.winds.ktx.person
import dev.teogor.winds.ktx.scm
import dev.teogor.winds.ktx.ticketSystem
import org.jetbrains.dokka.gradle.DokkaPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrains.kotlin.jvm) apply false
  alias(libs.plugins.jetbrains.compose.compiler) apply false
  alias(libs.plugins.metro) apply false
  alias(libs.plugins.ksp) apply false

  alias(libs.plugins.winds) apply true
  alias(libs.plugins.vanniktech.maven) apply true
  alias(libs.plugins.dokka) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.api.validator) apply true
}

winds {
  features {
    mavenPublishing = true
    docsGenerator = true
  }

  moduleMetadata {
    name = "Stitch"
    description =
      "\uD83E\uDEA1 Stitch handles the Room boilerplate, including automatic generation of repositories, dependency injection integration, and flexible customizations."
    yearCreated = 2024

    websiteUrl = "https://source.teogor.dev/stitch"
    apiDocsUrl = "https://source.teogor.dev/stitch"

    artifactDescriptor {
      group = "dev.teogor.stitch"
      name = "stitch"
      version = createVersion(1, 0, 0) {
        alphaRelease(2)
      }
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    }

    scm<Scm.GitHub> {
      owner = "teogor"
      repository = "stitch"
    }

    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "stitch"
    }

    licensedUnder(License.Apache2())

    person<Person.DeveloperContributor> {
      id = "teogor"
      name = "Teodor Grigor"
      email = "open-source@teogor.dev"
      url = "https://teogor.dev"
      roles = listOf("Code Owner", "Developer", "Designer", "Maintainer")
      timezone = "UTC+2"
      organization = "Teogor"
      organizationUrl = "https://github.com/teogor"
    }
  }

  publishing {
    enabled = false
    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    cascade = true
    automaticPublishing = true
  }
}

val excludedProjects = listOf(
  project.name,
  "app",
)

subprojects {
  apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude("**/build/**/*.kt")
      ktlint("1.2.1")
        .editorConfigOverride(
          mapOf(
            "ij_kotlin_allow_trailing_comma" to "true",
            "disabled_rules" to
              "filename," +
              "annotation,annotation-spacing," +
              "argument-list-wrapping," +
              "double-colon-spacing," +
              "enum-entry-name-case," +
              "multiline-if-else," +
              "no-empty-first-line-in-method-block," +
              "package-name," +
              "trailing-comma," +
              "spacing-around-angle-brackets," +
              "spacing-between-declarations-with-annotations," +
              "spacing-between-declarations-with-comments," +
              "unary-op-spacing," +
              "no-trailing-spaces," +
              "no-wildcard-imports," +
              "standard:function-naming,standard:property-naming," +
              "max-line-length",
          ),
        )
      licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
      trimTrailingWhitespace()
      endWithNewline()
    }
    format("kts") {
      target("**/*.kts")
      targetExclude("**/build/**/*.kts")
      // Look for the first line that doesn't have a block comment (assumed to be the license)
      licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
    }
    format("xml") {
      target("**/*.xml")
      targetExclude("**/build/**/*.xml")
      // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
      licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
    }
  }
}

apiValidation {
  /**
   * Subprojects that are excluded from API validation
   */
  ignoredProjects.addAll(excludedProjects)
}

subprojects {
  if (!excludedProjects.contains(project.name)) {
    apply<DokkaPlugin>()
  }
}

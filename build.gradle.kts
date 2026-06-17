/*
 * Copyright 2026 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.gradle.internal.os.OperatingSystem.current
import org.jetbrains.dokka.gradle.DokkaPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
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
        enablePublicationSigning = false
        optInForVanniktechPlugin = true
        cascade = true
        automaticPublishing = true
    }
}

val excludedProjects = listOf(
    project.name,
)

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt", "**/spotless/**/*.kt")
        ktlint("1.2.1")
            .editorConfigOverride(
                mapOf(
                    "indent_size" to "4",
                    "continuation_indent_size" to "4",
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
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**/*.gradle.kts", "**/spotless/**/*.gradle.kts")
        ktlint("1.2.1")
            .editorConfigOverride(
                mapOf(
                    "indent_size" to "4",
                    "continuation_indent_size" to "4",
                    "max_line_length" to "off",
                ),
            )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.kts"),
            "(^(?![\\/ ]\\*).*$)",
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml", "**/spotless/**/*.xml")
        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        trimTrailingWhitespace()
        endWithNewline()
        leadingTabsToSpaces(4)
    }
    format("json") {
        target("**/*.json")
        targetExclude("**/build/**/*.json")
        trimTrailingWhitespace()
        endWithNewline()
        leadingTabsToSpaces(4)
    }
    format("yaml") {
        target("**/*.yaml", "**/*.yml")
        targetExclude("**/build/**/*.yaml", "**/build/**/*.yml")
        trimTrailingWhitespace()
        endWithNewline()
        leadingTabsToSpaces(4)
    }
    format("toml") {
        target("**/*.toml")
        targetExclude("**/build/**/*.toml")
        trimTrailingWhitespace()
        endWithNewline()
        leadingTabsToSpaces(4)
    }
    format("properties") {
        target("**/*.properties")
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("markdown") {
        target("**/*.md")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

// Helper to identify core library modules
val Project.isLibraryModule: Boolean
    get() = !path.startsWith(":catalog") && this != rootProject

// Aggregator for all subproject tests (excluding samples)
tasks.register("allTests") {
    group = "verification"
    description = "Runs all tests in core Stitch modules"
    dependsOn(
        subprojects.filter { it.isLibraryModule }
            .map { it.tasks.matching { t -> t.name == "allTests" } },
    )
}

// Aggregator for all subproject API dumps (excluding samples)
tasks.register("apiDumpAll") {
    group = "verification"
    description = "Updates API compatibility files in core Stitch modules"
    dependsOn(
        subprojects.filter { it.isLibraryModule }
            .map { it.tasks.matching { t -> t.name == "apiDump" } },
    )
}

// Automated pre-commit installation
tasks.register<Exec>("installPreCommit") {
    description = "Installs pre-commit hooks"
    group = "verification"

    val preCommitExecutable = "pre-commit"
    val isWindows = current().isWindows
    val executable = if (isWindows) "$preCommitExecutable.exe" else preCommitExecutable

    commandLine(executable, "install")
}

tasks.register<Exec>("installPrePush") {
    description = "Installs pre-push hooks"
    group = "verification"

    val preCommitExecutable = "pre-commit"
    val isWindows = current().isWindows
    val executable = if (isWindows) "$preCommitExecutable.exe" else preCommitExecutable

    commandLine(executable, "install", "--hook-type", "pre-push")
}

tasks.named("installPreCommit") {
    finalizedBy("installPrePush")
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

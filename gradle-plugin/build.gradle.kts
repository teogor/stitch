/*
 * Copyright 2024 teogor (Teodor Grigor)
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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  alias(libs.plugins.gradle.publish)
  alias(libs.plugins.build.config)
  alias(libs.plugins.winds)
}

val javaVersion = JavaVersion.VERSION_11
java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = javaVersion.toString()
}

dependencies {
  api(project(":gradle-plugin-api"))

  implementation(gradleApi())
  implementation(libs.android.gradle.plugin)
  implementation(libs.kotlin.gradle.plugin)
  implementation(libs.ksp.gradle.plugin)
}

@Suppress("UnstableApiUsage")
gradlePlugin {
  website.set("https://source.teogor.dev/stitch")
  vcsUrl.set("https://github.com/teogor/stitch")

  plugins {
    register("stitchPlugin") {
      id = "dev.teogor.stitch"
      implementationClass = "dev.teogor.stitch.Plugin"
      displayName = "Stitch Plugin"
      description = "Stitch handles the Room boilerplate, including automatic generation of repositories, dependency injection integration, and flexible customizations."
      tags = listOf("dependency-injection", "kotlin", "productivity", "tools", "dsl", "code-generation", "teogor")
    }
  }
}

winds {
  mavenPublish {
    displayName = "Stitch Gradle Plugin"
    name = "gradle-plugin"
  }
}

buildConfig {
  packageName("dev.teogor.stitch")

  afterEvaluate {
    buildConfigField("String", "NAME", "\"${group}\"")
    buildConfigField("String", "VERSION", "\"${version}\"")
  }
}

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
import dev.teogor.stitch.api.DiFramework
import dev.teogor.stitch.api.OperationGenerationLevel
import dev.teogor.stitch.convention.androidTarget
import dev.teogor.stitch.convention.kmpLibraryAll

plugins {
  alias(libs.plugins.stitch.kmp.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.jetbrains.compose.compiler)
  alias(libs.plugins.ksp)
  id("dev.teogor.stitch") version "1.0.0-alpha02"
}

kotlin {
  kmpLibraryAll(project, "Shared") {
    sourceSets {
      commonMain.dependencies {
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.compose.material3)
        implementation(libs.compose.ui)
        implementation(libs.material.icons.core)
        implementation(libs.material.icons.extended)
        implementation(libs.compose.components.resources)
        implementation(libs.compose.uiToolingPreview)
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.androidx.lifecycle.runtimeCompose)

        implementation(libs.room.common)
        implementation(libs.room.runtime)
        implementation(libs.metro.runtime)
        implementation(project(":common"))
      }
      androidMain.dependencies {
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.room.runtime)
        implementation(libs.sqlite.bundled)
      }
      jvmMain.dependencies {
        implementation(libs.room.runtime)
        implementation(libs.sqlite.bundled)
      }
      jsMain.dependencies {
        implementation(libs.wrappers.browser)
      }
    }
  }

  androidTarget(project) {
    withHostTest {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  add("androidRuntimeClasspath", libs.androidx.ui.tooling)
  add("kspCommonMainMetadata", libs.room.compiler)
  add("kspAndroid", libs.room.compiler)
  add("kspJvm", libs.room.compiler)
  add("kspIosArm64", libs.room.compiler)
  add("kspIosSimulatorArm64", libs.room.compiler)

  add("kspCommonMainMetadata", project(":ksp"))
  add("kspAndroid", project(":ksp"))
  add("kspJvm", project(":ksp"))
  add("kspJs", project(":ksp"))
  add("kspWasmJs", project(":ksp"))
  add("kspIosArm64", project(":ksp"))
  add("kspIosSimulatorArm64", project(":ksp"))
}

stitch {
  generatedPackageName = "dev.teogor.stitch.catalog.generated"
  diFramework = DiFramework.METRO
  operationGenerationLevel = OperationGenerationLevel.ALL
}

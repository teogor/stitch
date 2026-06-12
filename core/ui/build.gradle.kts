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
import dev.teogor.stitch.convention.kmpLibraryAll

plugins {
  alias(libs.plugins.stitch.kmp.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.jetbrains.compose.compiler)
}

kotlin {
  kmpLibraryAll(project, "StitchCoreUi") {
    sourceSets {
      commonMain.dependencies {
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.compose.material3)
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
        implementation(libs.compose.uiToolingPreview)
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.androidx.lifecycle.runtimeCompose)
        implementation(project(":common"))
      }
      androidMain.dependencies {
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.core.ktx)
      }
    }
  }
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "core-ui"
    }
  }
}

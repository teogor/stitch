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

import dev.teogor.stitch.convention.androidTarget
import dev.teogor.stitch.convention.kmpLibraryAll
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.stitch.kmp.library)
    alias(libs.plugins.jetbrains.compose.compiler)
}

kotlin {
    kmpLibraryAll(
        project = project,
        frameworkBaseName = "StitchRuntime",
    )

    androidTarget(project) {
        namespace = "dev.teogor.stitch.runtime"
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.room.runtime)
                implementation(libs.compose.runtime)
                api(libs.kotlinx.coroutines.core)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.sqlite.bundled)
                implementation(libs.androidx.startup)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(libs.sqlite.bundled)
            }
        }

        val iosMain by getting {
            dependencies {
                api(libs.sqlite.bundled)
            }
        }

        val webMain by getting {
            dependencies {
                api(libs.sqlite.web)
                api(project(":stitch-web"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.wrappers.browser)
            }
        }

        @OptIn(ExperimentalWasmDsl::class)
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.wrappers.browser)
            }
        }
    }
}

winds {
    moduleMetadata {
        artifactDescriptor {
            name = "stitch-runtime"
        }
    }
}

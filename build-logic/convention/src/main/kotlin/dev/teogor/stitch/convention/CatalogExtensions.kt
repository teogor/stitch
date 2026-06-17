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

package dev.teogor.stitch.convention

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

/**
 * Returns the `libs` version catalog for this project.
 */
val Project.versionCatalog: VersionCatalog
    get() {
        val extension = extensions.getByType<VersionCatalogsExtension>()
        return try {
            extension.named("libs")
        } catch (e: Exception) {
            throw GradleException("Version catalog 'libs' not found", e)
        }
    }

/**
 * Returns the version string for [alias].
 */
fun VersionCatalog.requireVersion(alias: String): String = findVersion(alias).orElseThrow {
    GradleException("Version alias '$alias' not found in catalog")
}.requiredVersion

/**
 * Returns the version for [alias] parsed as an [Int].
 */
fun VersionCatalog.requireVersionInt(alias: String): Int = requireVersion(alias).toInt()

/**
 * Returns the dependency [Provider] for the library identified by [alias].
 */
fun VersionCatalog.requireLibrary(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow {
        GradleException("Library alias '$alias' not found in catalog")
    }

/**
 * Returns the plugin dependency [Provider] for [alias].
 */
fun VersionCatalog.requirePlugin(alias: String): Provider<PluginDependency> =
    findPlugin(alias).orElseThrow {
        GradleException("Plugin alias '$alias' not found in catalog")
    }

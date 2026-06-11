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
fun VersionCatalog.requireVersion(alias: String): String =
    findVersion(alias).orElseThrow {
        GradleException("Version alias '$alias' not found in catalog")
    }.requiredVersion

/**
 * Returns the version for [alias] parsed as an [Int].
 */
fun VersionCatalog.requireVersionInt(alias: String): Int =
    requireVersion(alias).toInt()

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

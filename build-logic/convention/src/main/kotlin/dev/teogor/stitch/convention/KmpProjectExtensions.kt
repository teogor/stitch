package dev.teogor.stitch.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures all supported KMP targets for a library module.
 */
@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.kmpLibraryAll(
    project: Project,
    frameworkBaseName: String,
    configure: KotlinMultiplatformExtension.() -> Unit = {},
) {
    // Register Android Target with defaults
    androidTarget(project)

    // iOS
    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = frameworkBaseName
            isStatic = true
        }
    }

    // Desktop
    jvm()

    // Web
    js { browser() }
    wasmJs { browser() }

    configure()
}

/**
 * Configures the Android target for a KMP library module with centralized defaults.
 */
fun KotlinMultiplatformExtension.androidTarget(
    project: Project,
    namespace: String = project.defaultNamespace,
    configure: Action<KotlinMultiplatformAndroidLibraryTarget> = Action {},
) {
    val libs = project.versionCatalog
    (this as ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryTarget>("android") {
        this.namespace = namespace
        compileSdk = libs.requireVersionInt("android-compileSdk")
        minSdk = libs.requireVersionInt("android-minSdk")

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        configure.execute(this)
    }
}

val Project.defaultNamespace: String
    get() {
        val basePackage = "dev.teogor.stitch"
        val suffix = path.replace(":", ".").removePrefix(".")
        return if (suffix.isEmpty()) basePackage else "$basePackage.$suffix"
    }

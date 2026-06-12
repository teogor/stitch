package dev.teogor.stitch.convention

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures all supported KMP targets for a library module.
 */
@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.kmpLibraryAll(
    frameworkBaseName: String,
    configure: KotlinMultiplatformExtension.() -> Unit = {},
) {
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

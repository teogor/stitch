import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "dev.teogor.stitch.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

// Resolve the version catalog outside of DependencyHandler scope
val buildLibs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    compileOnly("com.android.tools.build:gradle:${buildLibs.findVersion("agp").get().requiredVersion}")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${buildLibs.findVersion("kotlin").get().requiredVersion}")
    compileOnly("org.jetbrains.kotlin:compose-compiler-gradle-plugin:${buildLibs.findVersion("kotlin").get().requiredVersion}")
    compileOnly("com.google.devtools.ksp:symbol-processing-gradle-plugin:${buildLibs.findVersion("ksp").get().requiredVersion}")
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:${buildLibs.findVersion("hilt").get().requiredVersion}")
    compileOnly("dev.teogor.winds:dev.teogor.winds.gradle.plugin:${buildLibs.findVersion("winds").get().requiredVersion}")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "stitch.android.application"
            implementationClass = "dev.teogor.stitch.convention.AndroidApplicationConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "stitch.kotlin.library"
            implementationClass = "dev.teogor.stitch.convention.LibraryConventionPlugin"
        }
        register("gradlePlugin") {
            id = "stitch.gradle.plugin"
            implementationClass = "dev.teogor.stitch.convention.GradlePluginConventionPlugin"
        }
    }
}

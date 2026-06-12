@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.winds)
}

kotlin {
    js {
        browser()
        useEsModules()
    }
    wasmJs {
        browser()
        useEsModules()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.sqlite.common)
        }

        jsMain.dependencies {
            implementation(libs.sqlite.web)
            implementation(npm("@sqlite.org/sqlite-wasm", "3.50.1-build1"))
            implementation(npm("sqlite-wasm-worker", project.file("sqlite-wasm-worker")))
        }

        wasmJsMain.dependencies {
            implementation(libs.sqlite.web)
            implementation(libs.kotlinx.browser)
            implementation(npm("@sqlite.org/sqlite-wasm", "3.50.1-build1"))
            implementation(npm("sqlite-wasm-worker", project.file("sqlite-wasm-worker")))
        }
    }
}

winds {
    features {
        mavenPublishing = true
    }
}

# Installation & Setup

Get Stitch up and running in your project. Stitch is designed to work seamlessly with both Android-only and Kotlin Multiplatform (KMP) projects.

---

## 📦 Latest Version

The current stable release is: `1.0.0-alpha02`

---

## 🚀 Quick Setup

### 1. Version Catalog

We recommend using a Version Catalog to manage your dependencies.

```toml title="gradle/libs.versions.toml"
[versions]
stitch = "1.0.0-alpha02"

[libraries]
stitch-common = { module = "dev.teogor.stitch:stitch-common", version.ref = "stitch" }
stitch-ksp = { module = "dev.teogor.stitch:stitch-ksp", version.ref = "stitch" }

[plugins]
stitch = { id = "dev.teogor.stitch", version.ref = "stitch" }
```

### 2. Apply the Plugin

Apply the Stitch plugin in your module's `build.gradle.kts`.

```kotlin title="build.gradle.kts"
plugins {
    alias(libs.plugins.stitch)
}

stitch {
    generatedPackageName = "com.your.app.generated"
}

dependencies {
    implementation(libs.stitch.common)
    ksp(libs.stitch.ksp)
}
```

---

## 🌍 Kotlin Multiplatform (KMP)

Stitch is fully compatible with KMP. To use it in a multi-platform module, ensure you apply the KSP plugin to the appropriate targets.

```kotlin title="build.gradle.kts"
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.stitch.common)
            }
        }
    }
}

dependencies {
    // Add KSP to the common target or specific platform targets
    add("kspCommonMainMetadata", libs.stitch.ksp)
    // For specific platforms:
    // add("kspAndroid", libs.stitch.ksp)
    // add("kspIosArm64", libs.stitch.ksp)
}
```

---

## ⚙️ Configuration

For a full list of configuration options, including package overrides and DI settings, see the **[Reference Guide](../reference.md)**.

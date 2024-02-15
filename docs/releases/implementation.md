[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha02`](../releases.md)

### Plugin Releases

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha02 | [changelog 🔗](changelog/1.0.0-alpha02.md) | 15 Feb 2024  |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 06 Feb 2024  |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Stitch libraries and individual libraries, in
TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-stitch = "1.0.0-alpha02"

    [libraries]
    teogor-stitch-codegen = { module = "dev.teogor.stitch:codegen", version.ref = "teogor-stitch" }
    teogor-stitch-common = { module = "dev.teogor.stitch:common", version.ref = "teogor-stitch" }
    teogor-stitch-ksp = { module = "dev.teogor.stitch:ksp", version.ref = "teogor-stitch" }

    [plugins]
    teogor-stitch = { id = "dev.teogor.stitch", version.ref = "teogor-stitch" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
      // Stitch Plugin
      alias(libs.plugins.teogor.stitch)
    }

    dependencies {
      // Stitch Libraries
      implementation(libs.teogor.stitch.common)
      ksp(libs.teogor.stitch.ksp)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
      // Stitch Plugin
      alias libs.plugins.teogor.stitch
    }

    dependencies {
      // Stitch Libraries
      implementation libs.teogor.stitch.common
      ksp libs.teogor.stitch.ksp
    }
    ```

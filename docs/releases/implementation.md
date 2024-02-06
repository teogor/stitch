[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha01`](../releases.md)

### Plugin Releases

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 06 Feb 2024  |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Stitch libraries and individual libraries, in
TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    stitch = "1.0.0-alpha01"

    [libraries]
    stitch-common = { id = "dev.teogor.stitch", name = "common", version.ref = "stitch" }
    stitch-ksp = { id = "dev.teogor.stitch", name = "ksp", version.ref = "stitch" }

    [plugins]
    stitch = { id = "dev.teogor.stitch", version.ref = "stitch" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
      // Stitch Libraries
      implementation(libs.stitch.common)
      ksp(libs.stitch.ksp)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
      // Stitch Libraries
      implementation(libs.stitch.common)
      ksp(libs.stitch.ksp)
    }
    ```

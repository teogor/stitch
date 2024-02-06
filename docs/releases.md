[//]: # (This file was automatically generated - do not edit)

# Stitch

Stitch handles the Room boilerplate, including automatic generation of repositories, dependency injection integration, and flexible customizations.

---

### API Reference

* [`dev.teogor.stitch`](../reference/gradle-plugin)
* [`dev.teogor.stitch:codegen`](../reference/codegen)
* [`dev.teogor.stitch:common`](../reference/common)
* [`dev.teogor.stitch:gradle-plugin-api`](../reference/gradle-plugin-api)
* [`dev.teogor.stitch:ksp`](../reference/ksp)

### Release

|   Latest Update   | Stable Release | Beta Release | Alpha Release |
|:-----------------:|:--------------:|:------------:|:-------------:|
| February 06, 2024 |       -        |      -       | 1.0.0-alpha01 |

### Declaring dependencies

To add a dependency on Stitch, you must add the Maven repository to your project.
Read [Maven's repository for more information](https://repo.maven.apache.org/maven2/).

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

=== "Kotlin"

    ```kotlin
    plugins {
      id("dev.teogor.stitch") version "1.0.0-alpha01"
    }
    ```

=== "Groovy"

    ```groovy
    plugins {
      id 'dev.teogor.stitch' version '1.0.0-alpha01'
    }
    ```

### Feedback

Your feedback helps make Stitch better. We want to know if you discover new issues or have ideas
for improving this library. Before creating a new issue, please take a look at
the [existing ones](https://github.com/teogor/stitch) in this library. You can add your vote to an
existing issue by clicking the star button.

[Create a new issue](https://github.com/teogor/stitch/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha01

February 06, 2024

`dev.teogor.stitch:stitch-*:1.0.0-alpha01` is
released. [Version 1.0.0-alpha01 contains these commits.](https://github.com/teogor/stitch/compare/43cf9e9901d0b0f535761247c3869207a87e4016...1.0.0-alpha01)

**Initial Release** 🎊

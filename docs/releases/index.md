# Stitch

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

🪡 Stitch handles the Room boilerplate, including automatic generation of repositories, dependency injection integration, and flexible customizations.

[//]: # (REGION-API-REFERENCE)

API Reference
[`dev.teogor.stitch:stitch-*`](../)
[`dev.teogor.stitch:stitch-codegen`](../stitch-codegen)
[`dev.teogor.stitch:stitch-common`](../stitch-common)
[`dev.teogor.stitch:stitch-gradle-plugin`](../stitch-gradle-plugin)
[`dev.teogor.stitch:stitch-gradle-plugin-api`](../stitch-gradle-plugin-api)
[`dev.teogor.stitch:stitch-ksp`](../stitch-ksp)
[`dev.teogor.stitch:stitch-runtime`](../stitch-runtime)
[`dev.teogor.stitch:stitch-web`](../stitch-web)

[//]: # (REGION-API-REFERENCE)

[//]: # (REGION-RELEASE-TABLE)

| Latest Update   |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:----------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| June 26, 2026   |        -         |          -          |       -        |  1.0.0-alpha02  |

[//]: # (REGION-RELEASE-TABLE)

[//]: # (REGION-DEPENDENCIES)

## Declaring dependencies

To use Stitch in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorStitch = "1.0.0-alpha02"

        implementation "dev.teogor.stitch:stitch-codegen:$teogorStitch"
        implementation "dev.teogor.stitch:stitch-common:$teogorStitch"
        implementation "dev.teogor.stitch:stitch-gradle-plugin-api:$teogorStitch"
        implementation "dev.teogor.stitch:stitch-ksp:$teogorStitch"
        implementation "dev.teogor.stitch:stitch-runtime:$teogorStitch"
        implementation "dev.teogor.stitch:stitch-web:$teogorStitch"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorStitch = "1.0.0-alpha02"

        implementation("dev.teogor.stitch:stitch-codegen:$teogorStitch")
        implementation("dev.teogor.stitch:stitch-common:$teogorStitch")
        implementation("dev.teogor.stitch:stitch-gradle-plugin-api:$teogorStitch")
        implementation("dev.teogor.stitch:stitch-ksp:$teogorStitch")
        implementation("dev.teogor.stitch:stitch-runtime:$teogorStitch")
        implementation("dev.teogor.stitch:stitch-web:$teogorStitch")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Stitch documentation](../index.md#getting-started-with-stitch).

[//]: # (REGION-DEPENDENCIES)

[//]: # (REGION-FEEDBACK)

## Feedback

Your feedback helps make Stitch better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/stitch/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/stitch/issues/new){ .md-button }

[//]: # (REGION-FEEDBACK)

[//]: # (REGION-VERSION-CHANGELOG)



[//]: # (REGION-VERSION-CHANGELOG)

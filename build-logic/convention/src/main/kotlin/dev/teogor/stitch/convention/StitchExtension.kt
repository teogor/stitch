package dev.teogor.stitch.convention

import org.gradle.api.Project

/**
 * Project-wide Stitch configuration for convention plugins.
 */
interface StitchKmpExtension {
    // Add properties here as needed
}

fun Project.stitch(configure: StitchKmpExtension.() -> Unit) {
    // Implementation for the stitch extension
}

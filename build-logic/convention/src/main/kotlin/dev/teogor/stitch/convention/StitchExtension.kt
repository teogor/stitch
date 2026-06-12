package dev.teogor.stitch.convention

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import javax.inject.Inject

/**
 * Stitch configuration for convention plugins.
 */
open class StitchKmpExtension @Inject constructor(objects: ObjectFactory) {
    // Add properties here as needed, matching StitchExtension interface if possible
}

fun Project.stitch(configure: StitchKmpExtension.() -> Unit) {
    extensions.findByType<StitchKmpExtension>()?.configure()
}

internal fun Project.configureStitchExtension() {
    extensions.create<StitchKmpExtension>("stitch")
}

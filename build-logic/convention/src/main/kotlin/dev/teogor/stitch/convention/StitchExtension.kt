/*
 * Copyright 2026 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    if (extensions.findByName("stitch") == null) {
        extensions.create<StitchKmpExtension>("stitch")
    }
}

/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.stitch

import com.google.devtools.ksp.gradle.KspExtension
import dev.teogor.stitch.api.StitchExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

class Plugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val stitchExtension = extensions.create<StitchExtension>(
                name = "stitch",
            )

            with(target) {
                pluginManager.apply("com.google.devtools.ksp")

                afterEvaluate {
                    extensions.configure<KspExtension> {
                        arg(StitchSchemaArgProvider.from(stitchExtension))
                    }
                }
            }
        }
    }
}

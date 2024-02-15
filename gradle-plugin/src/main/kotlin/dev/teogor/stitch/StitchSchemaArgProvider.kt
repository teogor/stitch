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

import dev.teogor.stitch.api.OperationGenerationLevel
import dev.teogor.stitch.api.StitchExtension
import org.gradle.process.CommandLineArgumentProvider

class StitchSchemaArgProvider(
  private val addDocumentation: Boolean,
  private val enableOperationGeneration: Boolean,
  private val generatedPackageName: String,
  private val operationGenerationLevel: OperationGenerationLevel,
) : CommandLineArgumentProvider {

  override fun asArguments() = listOf(
    "stitch.addDocumentation=$addDocumentation",
    "stitch.enableOperationGeneration=$enableOperationGeneration",
    "stitch.generatedPackageName=$generatedPackageName",
    "stitch.operationGenerationLevel=$operationGenerationLevel",
  )

  companion object {
    fun from(stitchExtension: StitchExtension) = StitchSchemaArgProvider(
      addDocumentation = stitchExtension.addDocumentation,
      enableOperationGeneration = stitchExtension.enableOperationGeneration,
      generatedPackageName = stitchExtension.generatedPackageName,
      operationGenerationLevel = stitchExtension.operationGenerationLevel,
    )
  }
}

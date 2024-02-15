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

/**
 * Base implementation for the [StitchExtension] interface.
 *
 * This abstract class provides default values for all properties of the
 * [StitchExtension] interface.
 *
 * You can extend this class and override properties to customize the behavior
 * of Stitch code generation.
 *
 * @see StitchExtension
 */
abstract class StitchExtensionImpl : StitchExtension {

  /**
   * Controls whether to generate documentation comments in the generated code.
   *
   * By default, this is set to `true`.
   */
  override var addDocumentation: Boolean = true

  /**
   * Controls whether to generate operation functions for your Stitch schema.
   *
   * By default, this is set to `true`.
   */
  override var enableOperationGeneration: Boolean = true

  /**
   * Defines the level of generation for Stitch operation classes.
   *
   * This property offers more fine-grained control over how operation classes are generated.
   * You can choose from the following options:
   *
   * - **[OperationGenerationLevel.ALL]:** Generate operations for all methods in DAOs.
   * - **[OperationGenerationLevel.EXPLICIT]:** Generate operations only for methods annotated
   * with [dev.teogor.stitch.RawOperation].
   * - **[OperationGenerationLevel.AUTOMATIC]:** Use heuristics or rules to automatically choose
   * whether to generate for each method.
   * - **[OperationGenerationLevel.DISABLED]:** Do not generate any operation classes, even for
   * annotated methods.
   *
   * By default, this is set to [OperationGenerationLevel.EXPLICIT].
   *
   * @return The desired level of operation generation.
   */
  override var operationGenerationLevel: OperationGenerationLevel = OperationGenerationLevel.EXPLICIT

  /**
   * Specifies the base package name for generated code or artifacts.
   *
   * By default, this is an empty string, meaning the generated code will be
   * placed in the root package.
   */
  override var generatedPackageName: String = ""
}

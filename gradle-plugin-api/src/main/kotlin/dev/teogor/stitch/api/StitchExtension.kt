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

package dev.teogor.stitch.api

/**
 * Defines configuration options for Stitch code generation within your project.
 *
 * This interface provides properties that control various aspects of the generation
 * process. You can extend this interface and implement it in your plugin to customize Stitch
 * functionalities.
 *
 * @see dev.teogor.stitch.Plugin
 */
interface StitchExtension {
  /**
   * Controls whether to generate documentation comments in the generated code.
   *
   * By default, this is set to `true`. Setting it to `false` will disable documentation
   * generation.
   *
   * @return `true` if documentation is generated, `false` otherwise.
   */
  var addDocumentation: Boolean

  /**
   * Controls the overall generation of operation classes for DAO methods.
   *
   * Use this property to enable or disable operation generation entirely.
   * By default, this is set to `true`. Setting it to `false` will disable operation generation
   * even for methods annotated with [dev.teogor.stitch.RawOperation].
   *
   * For more granular control over operation generation, consider using the
   * [operationGenerationLevel] property.
   *
   * @return `true` to enable operation generation, `false` to disable it.
   */
  var enableOperationGeneration: Boolean

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
  var operationGenerationLevel: OperationGenerationLevel

  /**
   * Specifies the base package name for generated code or artifacts.
   *
   * This property defines the root package where your Stitch-generated code will be placed.
   * Ensure it aligns with your project's package structure to avoid conflicts.
   *
   * @return The base package name for generated code.
   */
  var generatedPackageName: String
}

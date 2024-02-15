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
  override var generateOperations: Boolean = true

  /**
   * Specifies the base package name for generated code or artifacts.
   *
   * By default, this is an empty string, meaning the generated code will be
   * placed in the root package.
   */
  override var generatedPackageName: String = ""
}

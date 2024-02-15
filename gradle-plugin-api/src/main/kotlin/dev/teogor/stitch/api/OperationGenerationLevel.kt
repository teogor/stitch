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
 * Defines the level of generation for Stitch operation classes.
 *
 * This enum controls how the plugin generates operation classes from DAO
 * methods.
 * You can configure the desired level through plugin options or environment
 * variables.
 *
 * @see dev.teogor.stitch.RawOperation
 *
 * @property ALL Generate operations for all methods in DAOs.
 * @property EXPLICIT Generate operations only for methods annotated with
 * [dev.teogor.stitch.RawOperation].
 * @property AUTOMATIC Automatically choose whether to generate based on heuristics
 * or rules.
 * @property DISABLED Do not generate any operation classes, even for annotated
 * methods.
 */
enum class OperationGenerationLevel {
  ALL,
  EXPLICIT,
  AUTOMATIC,
  DISABLED,
  ;

  companion object {
    /**
     * Converts a string representation to the corresponding [OperationGenerationLevel].
     *
     * This function supports case-insensitive matching and throws an exception for invalid input.
     *
     * @param string The string to convert.
     * @return The corresponding [OperationGenerationLevel] or throws an [IllegalArgumentException].
     */
    fun from(string: String): OperationGenerationLevel {
      return values().firstOrNull { it.name.lowercase() == string.lowercase() }
        ?: throw IllegalArgumentException("Invalid OperationGenerationLevel: $string")
    }
  }
}

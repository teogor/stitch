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
 * Supported dependency injection frameworks for Stitch code generation.
 */
enum class DiFramework {
  /**
   * No dependency injection framework. Stitch will generate vanilla code.
   */
  NONE,

  /**
   * Integrate with Metro for dependency injection.
   *
   * @see <a href="https://github.com/ZacSweers/metro">Metro</a>
   */
  METRO,

  /**
   * Integrate with Hilt for dependency injection.
   *
   * @see <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt</a>
   */
  HILT,

  /**
   * Integrate with Dagger for dependency injection.
   *
   * @see <a href="https://dagger.dev/">Dagger</a>
   */
  DAGGER,

  /**
   * Integrate with Koin for dependency injection.
   *
   * @see <a href="https://insert-koin.io/">Koin</a>
   */
  KOIN,

  /**
   * Use a custom dependency injection setup.
   *
   * You can provide a custom `@Inject` annotation using the `injectAnnotation` property.
   */
  CUSTOM,
  ;

  companion object {
    /**
     * Converts a string representation to the corresponding [DiFramework].
     *
     * This function supports case-insensitive matching and returns [METRO] for invalid input.
     *
     * @param string The string to convert.
     * @return The corresponding [DiFramework] or [METRO] if not found.
     */
    fun from(string: String): DiFramework {
      return entries.firstOrNull { it.name.lowercase() == string.lowercase() }
        ?: METRO
    }
  }
}

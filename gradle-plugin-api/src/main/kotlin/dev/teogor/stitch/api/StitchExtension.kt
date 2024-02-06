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
 * Interface for configuring Stitch's behavior and features.
 *
 * This interface allows you to:
 *
 * - Enable or disable documentation generation.
 * - Control the generation of operations (plugins or similar functionality).
 * - Set the base package name for generated code or artifacts.
 */
interface StitchExtension {
    /**
     * Enables or disables the generation of documentation.
     *
     * Set to `true` to generate documentation, `false` to disable it.
     */
    var addDocumentation: Boolean

    /**
     * Enables or disables the generation of operations.
     *
     * Set to `true` to generate operations, `false` to disable it.
     */
    var generateOperations: Boolean

    /**
     * Specifies the base package name for generated code or artifacts.
     *
     * Ensure this matches your project's package structure.
     */
    var generatedPackageName: String
}

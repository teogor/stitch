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
 * Defines the visibility levels for generated Stitch code.
 */
enum class Visibility {
    /**
     * Generated code will be public.
     */
    PUBLIC,

    /**
     * Generated code will be internal to the module.
     */
    INTERNAL,

    ;

    companion object {
        /**
         * Converts a string representation to the corresponding [Visibility].
         *
         * This function supports case-insensitive matching and returns [PUBLIC] for invalid input.
         *
         * @param string The string to convert.
         * @return The corresponding [Visibility] or [PUBLIC] if not found.
         */
        fun from(string: String): Visibility {
            return entries.firstOrNull { it.name.lowercase() == string.lowercase() }
                ?: PUBLIC
        }
    }
}

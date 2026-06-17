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

package dev.teogor.stitch.runtime

/**
 * Defines the strategy for resolving the database file path across different platforms.
 */
sealed interface DatabasePath {
    /**
     * Internal application storage.
     * - Android: `/data/data/{pkg}/databases/`
     * - iOS: `NSDocumentDirectory`
     * - JVM: User home directory or current working directory.
     */
    data object Internal : DatabasePath

    /**
     * Temporary storage (cache).
     * - Android: `context.cacheDir`
     * - iOS: `NSCachesDirectory`
     */
    data object Temporary : DatabasePath

    /**
     * A custom directory path.
     *
     * @property absoluteDirectoryPath The absolute path to the directory where the database should be stored.
     */
    data class Custom(val absoluteDirectoryPath: String) : DatabasePath

    /**
     * In-memory database.
     * Useful for testing or volatile data.
     */
    data object InMemory : DatabasePath
}

/**
 * Internal resolver to map [DatabasePath] strategies to actual file paths.
 */
@PublishedApi
internal expect object StitchPathResolver {
    /**
     * Resolves the final path for the database file.
     *
     * @param name The database name.
     * @param strategy The path strategy to resolve.
     * @return The absolute path to the database file.
     */
    fun resolve(name: String, strategy: DatabasePath): String
}

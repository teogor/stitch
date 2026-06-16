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

import androidx.room3.RoomDatabase

/**
 * Robust entry point for Room 3 KMP database instantiation.
 *
 * This object provides a unified way to create [RoomDatabase.Builder] instances across
 * all supported KMP platforms, handling path resolution and platform-specific builder creation.
 */
object StitchRoom {
  /**
   * Constructs a managed platform-wrapped RoomDatabase.Builder inside common KMP layers.
   *
   * @param T The type of the RoomDatabase.
   * @param name The name of the database file. Ignored if [StitchRoomConfig.pathStrategy] is [DatabasePath.InMemory].
   * @param factory The factory function to create the RoomDatabase instance.
   * @param block Configuration block for [StitchRoomConfig].
   * @return A [RoomDatabase.Builder] configured according to the provided [block].
   */
  inline fun <reified T : RoomDatabase> databaseBuilder(
    name: String,
    noinline factory: () -> T,
    block: StitchRoomConfig<T>.() -> Unit = {},
  ): RoomDatabase.Builder<T> {
    val config = StitchRoomConfig<T>().apply(block)

    val builder = if (config.pathStrategy is DatabasePath.InMemory) {
      createPlatformInMemoryBuilder(factory)
    } else {
      val fullyQualifiedPath = StitchPathResolver.resolve(name, config.pathStrategy)
      createPlatformBuilder(fullyQualifiedPath, factory)
    }

    config.applyTo(builder)

    return builder
  }

  /**
   * Constructs an in-memory platform-wrapped RoomDatabase.Builder inside common KMP layers.
   *
   * @param T The type of the RoomDatabase.
   * @param factory The factory function to create the RoomDatabase instance.
   * @param block Configuration block for [StitchRoomConfig].
   * @return A [RoomDatabase.Builder] configured according to the provided [block].
   */
  inline fun <reified T : RoomDatabase> inMemoryDatabaseBuilder(
    noinline factory: () -> T,
    block: StitchRoomConfig<T>.() -> Unit = {},
  ): RoomDatabase.Builder<T> {
    val config = StitchRoomConfig<T>().apply {
      pathStrategy = DatabasePath.InMemory
      block()
    }
    val builder = createPlatformInMemoryBuilder(factory)
    config.applyTo(builder)
    return builder
  }
}

/**
 * Internal system bridge linking common configuration maps to native Room database builder interfaces.
 */
@PublishedApi
internal expect inline fun <reified T : RoomDatabase> createPlatformBuilder(
  resolvedPath: String,
  noinline factory: () -> T,
): RoomDatabase.Builder<T>

/**
 * Internal system bridge for creating in-memory database builders.
 */
@PublishedApi
internal expect inline fun <reified T : RoomDatabase> createPlatformInMemoryBuilder(
  noinline factory: () -> T,
): RoomDatabase.Builder<T>

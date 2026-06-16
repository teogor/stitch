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
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.jvm.JvmSynthetic

/**
 * Configuration DSL for Room KMP database instances.
 */
class StitchRoomConfig<T : RoomDatabase> {
  /**
   * The strategy used to resolve the database file path.
   * Defaults to [DatabasePath.Internal].
   */
  var pathStrategy: DatabasePath = DatabasePath.Internal

  /**
   * Custom SQLite driver. If null, the platform-best driver will be used.
   * On most platforms, the bundled SQLite driver is recommended.
   */
  var driver: SQLiteDriver? = null

  /**
   * Dispatcher used for database queries.
   * Defaults to [StitchRuntime.ioDispatcher].
   */
  var queryDispatcher: CoroutineDispatcher = StitchRuntime.ioDispatcher

  /**
   * Whether to use the bundled SQLite driver.
   * If true, it overrides the [driver] property with a bundled driver if [driver] is null.
   */
  var useBundledSQLite: Boolean = true

  /**
   * The journal mode for the database.
   * Defaults to [RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING].
   */
  var journalMode: RoomDatabase.JournalMode = RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING

  /**
   * Whether to enable SQL query logging.
   * If true, all executed SQL queries and their parameters will be logged using [logger].
   */
  var loggingEnabled: Boolean = false

  /**
   * The logger function used for SQL query logging.
   * Defaults to printing to the console.
   */
  var logger: (String) -> Unit = { println(it) }

  private val migrations = mutableListOf<Migration>()
  private val callbacks = mutableListOf<RoomDatabase.Callback>()
  private val typeConverters = mutableListOf<Any>()
  private var fallbackToDestructiveMigration: Boolean = false
  private var dropAllTablesOnDestructiveMigration: Boolean = true

  /**
   * Adds migrations to the database builder.
   *
   * @param migration The migrations to add.
   */
  fun addMigrations(vararg migration: Migration) {
    migrations.addAll(migration)
  }

  /**
   * Adds callbacks to the database builder.
   *
   * @param callback The callbacks to add.
   */
  fun addCallbacks(vararg callback: RoomDatabase.Callback) {
    callbacks.addAll(callback)
  }

  /**
   * Adds type converters to the database builder.
   *
   * @param converter The type converters to add.
   */
  fun addTypeConverters(vararg converter: Any) {
    typeConverters.addAll(converter)
  }

  /**
   * Allows Room to destructively recreate database tables if Migrations are not found.
   *
   * @param dropAllTables Whether to drop all tables or only Room-managed ones.
   */
  fun fallbackToDestructiveMigration(dropAllTables: Boolean = true) {
    fallbackToDestructiveMigration = true
    dropAllTablesOnDestructiveMigration = dropAllTables
  }

  /**
   * Internal function to apply this configuration to a [RoomDatabase.Builder].
   */
  @PublishedApi
  @JvmSynthetic
  internal fun applyTo(builder: RoomDatabase.Builder<T>) {
    builder.setQueryCoroutineContext(queryDispatcher)
    builder.setJournalMode(journalMode)

    if (migrations.isNotEmpty()) {
      builder.addMigrations(*migrations.toTypedArray())
    }

    if (callbacks.isNotEmpty()) {
      callbacks.forEach { builder.addCallback(it) }
    }

    if (typeConverters.isNotEmpty()) {
      typeConverters.forEach { builder.addTypeConverter(it) }
    }

    if (fallbackToDestructiveMigration) {
      builder.fallbackToDestructiveMigration(dropAllTablesOnDestructiveMigration)
    }

    val sqliteDriver = driver ?: if (useBundledSQLite) {
      getBundledSQLiteDriver()
    } else {
      null
    }

    sqliteDriver?.let {
      val finalDriver = if (loggingEnabled) {
        LoggingSQLiteDriver(it, logger)
      } else {
        it
      }
      builder.setDriver(finalDriver)
    }
  }
}

/**
 * Internal system bridge to get the bundled SQLite driver for the platform.
 */
@PublishedApi
internal expect fun getBundledSQLiteDriver(): SQLiteDriver?

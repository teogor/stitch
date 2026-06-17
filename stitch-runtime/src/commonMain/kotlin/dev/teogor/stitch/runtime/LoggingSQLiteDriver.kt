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

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.SQLiteStatement

/**
 * A [SQLiteDriver] decorator that logs all executed SQL queries and their parameters.
 */
internal expect class LoggingSQLiteDriver(
    delegate: SQLiteDriver,
    logger: (String) -> Unit,
) : SQLiteDriver

/**
 * A [SQLiteConnection] decorator that logs prepared SQL queries.
 */
internal expect class LoggingSQLiteConnection(
    delegate: SQLiteConnection,
    logger: (String) -> Unit,
) : SQLiteConnection

/**
 * A [SQLiteStatement] decorator that logs the SQL query and its bound parameters when executed.
 */
internal expect class LoggingSQLiteStatement(
    sql: String,
    delegate: SQLiteStatement,
    logger: (String) -> Unit,
) : SQLiteStatement

internal fun formatLog(sql: String, bindings: Map<Int, Any?>): String {
    val formattedArgs = if (bindings.isEmpty()) {
        ""
    } else {
        val sortedValues = bindings.keys.sorted().map { bindings[it] }
        "\n  args: ${sortedValues.joinToString(", ")}"
    }
    return "SQLite: $sql$formattedArgs"
}

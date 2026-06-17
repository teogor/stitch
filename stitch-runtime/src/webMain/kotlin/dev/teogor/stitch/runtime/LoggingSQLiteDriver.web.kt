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

internal actual class LoggingSQLiteDriver actual constructor(
    private val delegate: SQLiteDriver,
    private val logger: (String) -> Unit,
) : SQLiteDriver by delegate {
    override suspend fun open(fileName: String): SQLiteConnection {
        return LoggingSQLiteConnection(delegate.open(fileName), logger)
    }
}

internal actual class LoggingSQLiteConnection actual constructor(
    private val delegate: SQLiteConnection,
    private val logger: (String) -> Unit,
) : SQLiteConnection by delegate {
    override suspend fun prepare(sql: String): SQLiteStatement {
        return LoggingSQLiteStatement(sql, delegate.prepare(sql), logger)
    }
}

internal actual class LoggingSQLiteStatement actual constructor(
    private val sql: String,
    private val delegate: SQLiteStatement,
    private val logger: (String) -> Unit,
) : SQLiteStatement by delegate {
    private val bindings = mutableMapOf<Int, Any?>()
    private var hasLogged = false

    override fun bindBlob(index: Int, value: ByteArray) {
        bindings[index] = "Blob(${value.size} bytes)"
        delegate.bindBlob(index, value)
    }

    override fun bindDouble(index: Int, value: Double) {
        bindings[index] = value
        delegate.bindDouble(index, value)
    }

    override fun bindLong(index: Int, value: Long) {
        bindings[index] = value
        delegate.bindLong(index, value)
    }

    override fun bindText(index: Int, value: String) {
        bindings[index] = value
        delegate.bindText(index, value)
    }

    override fun bindNull(index: Int) {
        bindings[index] = null
        delegate.bindNull(index)
    }

    override fun bindBoolean(index: Int, value: Boolean) {
        bindings[index] = value
        delegate.bindBoolean(index, value)
    }

    override fun bindInt(index: Int, value: Int) {
        bindings[index] = value
        delegate.bindInt(index, value)
    }

    override fun bindFloat(index: Int, value: Float) {
        bindings[index] = value
        delegate.bindFloat(index, value)
    }

    override suspend fun step(): Boolean {
        if (!hasLogged) {
            logger(formatLog(sql, bindings))
            hasLogged = true
        }
        return delegate.step()
    }

    override fun reset() {
        hasLogged = false
        delegate.reset()
    }

    override fun clearBindings() {
        bindings.clear()
        delegate.clearBindings()
    }
}

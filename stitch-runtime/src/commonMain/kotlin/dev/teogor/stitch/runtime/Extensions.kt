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
import androidx.room3.immediateTransaction
import androidx.room3.useWriterConnection

/**
 * Wraps a block of code in a write transaction.
 *
 * This uses [useWriterConnection] and [immediateTransaction] internally
 * to provide a familiar API for common code.
 *
 * @param block The block of code to execute within the transaction.
 * @return The result of the transaction block.
 */
suspend fun <R> RoomDatabase.withTransaction(
  block: suspend () -> R,
): R = useWriterConnection { transactor ->
  transactor.immediateTransaction {
    block()
  }
}

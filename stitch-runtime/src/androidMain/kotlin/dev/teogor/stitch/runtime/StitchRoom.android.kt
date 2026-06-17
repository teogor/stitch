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

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

@PublishedApi
internal actual inline fun <reified T : RoomDatabase> createPlatformBuilder(
    resolvedPath: String,
    noinline factory: () -> T,
): RoomDatabase.Builder<T> {
    return Room.databaseBuilder(
        context = StitchRuntime.getContext().context,
        name = resolvedPath,
        factory = factory,
    )
}

@PublishedApi
internal actual inline fun <reified T : RoomDatabase> createPlatformInMemoryBuilder(
    noinline factory: () -> T,
): RoomDatabase.Builder<T> {
    return Room.inMemoryDatabaseBuilder(
        context = StitchRuntime.getContext().context,
        factory = factory,
    )
}

@PublishedApi
internal actual fun getBundledSQLiteDriver(): SQLiteDriver? = BundledSQLiteDriver()

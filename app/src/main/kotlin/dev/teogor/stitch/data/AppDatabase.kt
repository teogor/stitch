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

package dev.teogor.stitch.data

// import dev.teogor.stitch.data.dao.GameWithBoardDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.teogor.stitch.data.converters.Converters
import dev.teogor.stitch.data.converters.DurationConverter
import dev.teogor.stitch.data.converters.ZonedDateTimeConverter
import dev.teogor.stitch.data.dao.SavedGameDao
import dev.teogor.stitch.data.dao.TestingKindDao
import dev.teogor.stitch.data.model.SavedGame
import dev.teogor.stitch.data.model.TestingKind

@Database(
    entities = [
        TestingKind::class,
        SavedGame::class,
    ],
    version = 1,
)
@TypeConverters(
    Converters::class,
    DurationConverter::class,
    ZonedDateTimeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testingKindDao(): TestingKindDao

    abstract fun savedGameDao(): SavedGameDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "main_database",
                ).build()
            }

            return INSTANCE as AppDatabase
        }
    }
}

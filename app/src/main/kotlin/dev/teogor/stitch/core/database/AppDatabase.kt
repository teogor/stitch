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

package dev.teogor.stitch.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.teogor.stitch.core.database.dao.SavedGameDao
import dev.teogor.stitch.core.database.dao.TestingKindDao
import dev.teogor.stitch.core.database.model.SavedGame
import dev.teogor.stitch.core.database.model.TestingKind
import dev.teogor.stitch.core.database.util.Converters
import dev.teogor.stitch.core.database.util.DurationConverter
import dev.teogor.stitch.core.database.util.ZonedDateTimeConverter

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

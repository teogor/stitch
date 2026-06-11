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

package dev.teogor.stitch.core.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.teogor.stitch.core.data.dao.BlackListStoreDao
import dev.teogor.stitch.core.data.dao.HistoryDao
import dev.teogor.stitch.core.data.dao.LyricsDao
import dev.teogor.stitch.core.data.dao.PlaylistDao
import dev.teogor.stitch.core.data.model.BlackListStoreEntity
import dev.teogor.stitch.core.data.model.History
import dev.teogor.stitch.core.data.model.Lyrics
import dev.teogor.stitch.core.data.model.Playlist
import dev.teogor.stitch.core.data.model.Song

@Database(
  entities = [
    BlackListStoreEntity::class,
    History::class,
    Lyrics::class,
    Playlist::class,
    Song::class,
  ],
  version = 2,
)
@TypeConverters
abstract class BeatifydDatabase : RoomDatabase() {

  abstract fun blackListStoreDao(): BlackListStoreDao

  abstract fun historyDao(): HistoryDao

  abstract fun lyricsDao(): LyricsDao

  abstract fun playlistDao(): PlaylistDao

  companion object {
    private var INSTANCE: BeatifydDatabase? = null

    fun getInstance(context: Context): BeatifydDatabase {
      if (INSTANCE == null) {
        INSTANCE = Room.databaseBuilder<BeatifydDatabase>(
          context = context,
          name = "main_database",
        ).setDriver(BundledSQLiteDriver())
          .build()
      }

      return INSTANCE as BeatifydDatabase
    }
  }
}

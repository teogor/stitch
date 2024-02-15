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

package com.zeoowl.beatifyd.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zeoowl.beatifyd.core.data.dao.BlackListStoreDao
import com.zeoowl.beatifyd.core.data.dao.HistoryDao
import com.zeoowl.beatifyd.core.data.dao.LyricsDao
import com.zeoowl.beatifyd.core.data.dao.PlaylistDao
import com.zeoowl.beatifyd.core.data.model.BlackListStoreEntity
import com.zeoowl.beatifyd.core.data.model.History
import com.zeoowl.beatifyd.core.data.model.Lyrics
import com.zeoowl.beatifyd.core.data.model.Playlist
import com.zeoowl.beatifyd.core.data.model.Song

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
abstract class AppDatabase : RoomDatabase() {

  abstract fun blackListStoreDao(): BlackListStoreDao

  abstract fun historyDao(): HistoryDao

  abstract fun lyricsDao(): LyricsDao

  abstract fun playlistDao(): PlaylistDao

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

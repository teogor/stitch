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

package com.zeoowl.beatifyd.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zeoowl.beatifyd.core.data.model.Lyrics

@Dao
interface LyricsDao {
  @Query("SELECT * FROM Lyrics WHERE songId =:songId LIMIT 1")
  fun lyricsWithSongId(songId: Int): Lyrics?

  @Insert
  fun insertLyrics(lyricsEntity: Lyrics)

  @Delete
  fun deleteLyrics(lyricsEntity: Lyrics)

  @Update
  fun updateLyrics(lyricsEntity: Lyrics)
}

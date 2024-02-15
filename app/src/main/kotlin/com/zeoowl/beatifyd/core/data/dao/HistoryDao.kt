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
import androidx.room.Query
import androidx.room.Upsert
import com.zeoowl.beatifyd.core.data.model.History
import dev.teogor.stitch.ExplicitEntities
import kotlinx.coroutines.flow.Flow

@Dao
@ExplicitEntities(
  entities = [
    History::class,
  ],
)
interface HistoryDao {
  companion object {
    private const val HISTORY_LIMIT = 100
  }

  @Upsert
  suspend fun upsertSongInHistory(history: History)

  @Query("DELETE FROM History WHERE id= :songId")
  fun deleteSongInHistory(songId: Long)

  @Query("SELECT * FROM History ORDER BY time_played DESC LIMIT $HISTORY_LIMIT")
  fun historySongs(): Flow<List<History>>

  @Query("DELETE FROM History")
  suspend fun clearHistory()
}

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

package dev.teogor.stitch.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.ZonedDateTime

@Entity(
  tableName = "saved_games",
)
data class SavedGame(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val uid: Long = 0L,
  @ColumnInfo(name = "board_id") val boardId: Long,
  @ColumnInfo(name = "current_board") val currentBoard: String,
  @ColumnInfo(name = "notes") val notes: String,
  @ColumnInfo(name = "timer") val timer: Duration,
  @ColumnInfo(name = "completed", defaultValue = "false") val completed: Boolean = false,
  @ColumnInfo(name = "give_up", defaultValue = "false") val giveUp: Boolean = false,
  @ColumnInfo(name = "mistakes", defaultValue = "0") val mistakes: Int = 0,
  @ColumnInfo(name = "can_continue") val canContinue: Boolean = true,
  @ColumnInfo(name = "last_played") val lastPlayed: ZonedDateTime? = null,
  @ColumnInfo(name = "started_at") val startedAt: ZonedDateTime,
  @ColumnInfo(name = "finished_at") val finishedAt: ZonedDateTime? = null,
  @ColumnInfo(name = "hints_used", defaultValue = "0") val hintsUsed: Int = 0,
)

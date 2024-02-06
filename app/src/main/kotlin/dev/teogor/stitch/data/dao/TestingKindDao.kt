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

package dev.teogor.stitch.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.teogor.stitch.data.model.SavedGame
import kotlinx.coroutines.flow.Flow

@Dao
interface TestingKindDao {
    @Query("SELECT * FROM testing_kind")
    fun getAll(): Flow<List<SavedGame>>

    @Query("SELECT * FROM testing_kind WHERE board_id == :id")
    suspend fun get(id: Long): SavedGame?

    @Query(
        "SELECT * FROM testing_kind " +
            "WHERE completed == 'false' " +
            "ORDER BY board_id DESC " +
            "LIMIT 1",
    )
    fun getLast(): Flow<SavedGame?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedGame: SavedGame): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(savedGame: SavedGame)

    @Delete
    suspend fun delete(savedGame: SavedGame)
}

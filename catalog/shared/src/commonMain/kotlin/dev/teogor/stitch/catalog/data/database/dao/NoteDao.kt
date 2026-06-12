/*
 * Copyright 2026 teogor (Teodor Grigor)
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

package dev.teogor.stitch.catalog.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.StitchName
import dev.teogor.stitch.catalog.data.database.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
@StitchName(repository = "NoteRepository")
interface NoteEntityDao {
  @Query("SELECT * FROM NoteEntity")
  fun getAllNotes(): Flow<List<NoteEntity>>

  @Insert
  @RawOperation
  suspend fun insertNote(note: NoteEntity)

  @Query("DELETE FROM NoteEntity WHERE id = :id")
  suspend fun deleteNoteById(id: Long)
}

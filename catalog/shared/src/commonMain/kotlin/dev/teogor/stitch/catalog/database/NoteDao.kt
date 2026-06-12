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

package dev.teogor.stitch.catalog.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.StitchName
import kotlinx.coroutines.flow.Flow

@Dao
@StitchName("NotesManager")
interface NoteDao {
  @Query("SELECT * FROM Note")
  fun getAllNotes(): Flow<List<Note>>

  @Insert
  @RawOperation
  suspend fun insertNote(note: Note)

  @Query("DELETE FROM Note WHERE id = :id")
  suspend fun deleteNoteById(id: Long)
}

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

package dev.teogor.stitch.catalog.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.teogor.stitch.catalog.domain.usecase.AddNoteUseCase
import dev.teogor.stitch.catalog.domain.usecase.DeleteNoteUseCase
import dev.teogor.stitch.catalog.domain.usecase.GetNotesUseCase
import dev.teogor.stitch.catalog.presentation.mapper.toUi
import dev.teogor.stitch.catalog.presentation.model.NoteUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
  private val getNotesUseCase: GetNotesUseCase,
  private val addNoteUseCase: AddNoteUseCase,
  private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

  val notes: StateFlow<List<NoteUi>> = getNotesUseCase()
    .map { notes -> notes.map { it.toUi() } }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList(),
    )

  fun addNote(title: String, content: String) {
    viewModelScope.launch {
      addNoteUseCase(title, content)
    }
  }

  fun deleteNote(id: Long) {
    viewModelScope.launch {
      deleteNoteUseCase(id)
    }
  }
}

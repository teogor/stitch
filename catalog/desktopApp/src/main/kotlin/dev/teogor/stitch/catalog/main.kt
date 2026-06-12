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

package dev.teogor.stitch.catalog

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.teogor.stitch.catalog.data.database.AppDatabase
import dev.teogor.stitch.catalog.data.database.AppDatabaseConstructor
import dev.teogor.stitch.catalog.data.mapper.NoteMapper
import dev.teogor.stitch.catalog.domain.usecase.AddNoteUseCase
import dev.teogor.stitch.catalog.domain.usecase.DeleteNoteUseCase
import dev.teogor.stitch.catalog.domain.usecase.GetNotesUseCase
import dev.teogor.stitch.catalog.generated.data.repository.impl.NoteRepositoryImpl
import dev.teogor.stitch.catalog.presentation.ui.NoteViewModel
import java.io.File

fun main() = application {
  val viewModel = remember {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "note_db.db")
    val db = Room.databaseBuilder<AppDatabase>(
      name = dbFile.absolutePath,
      factory = { AppDatabaseConstructor.initialize() },
    )
      .setDriver(BundledSQLiteDriver())
      .build()

    val dao = db.noteDao()
    val repository = NoteRepositoryImpl(
      dao = dao,
      db = db,
      mapper = NoteMapper(),
    )

    NoteViewModel(
      getNotesUseCase = GetNotesUseCase(repository),
      addNoteUseCase = AddNoteUseCase(repository),
      deleteNoteUseCase = DeleteNoteUseCase(repository),
    )
  }

  Window(
    onCloseRequest = ::exitApplication,
    title = "Stitch Catalog",
  ) {
    App(viewModel)
  }
}

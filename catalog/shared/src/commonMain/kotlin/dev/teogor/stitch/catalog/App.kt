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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.stitch.catalog.presentation.ui.NoteViewModel
import dev.teogor.stitch.catalog.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ktlint:standard:function-naming")
fun App(viewModel: NoteViewModel) {
  AppTheme {
    Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
          title = {
            Text(
              text = "Stitch Catalog - Notes",
              style = MaterialTheme.typography.titleLarge,
            )
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
          ),
        )
      },
      floatingActionButton = {
        var showAddDialog by remember { mutableStateOf(false) }
        FloatingActionButton(onClick = { showAddDialog = true }) {
          Icon(Icons.Default.Add, contentDescription = "Add Note")
        }

        if (showAddDialog) {
          AddNoteDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, content ->
              viewModel.addNote(title, content)
              showAddDialog = false
            },
          )
        }
      },
    ) { innerPadding ->
      val notes by viewModel.notes.collectAsState()

      Surface(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding),
        color = MaterialTheme.colorScheme.background,
      ) {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          items(notes, key = { it.id }) { note ->
            Card(
              modifier = Modifier.fillMaxWidth(),
            ) {
              Row(
                modifier = Modifier
                  .padding(16.dp)
                  .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                  Text(text = note.content, style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = { viewModel.deleteNote(note.id) }) {
                  Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
@Suppress("ktlint:standard:function-naming")
fun AddNoteDialog(onDismiss: () -> Unit, onAdd: (String, String) -> Unit) {
  var title by remember { mutableStateOf("") }
  var content by remember { mutableStateOf("") }

  androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 8.dp,
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(text = "Add New Note", style = MaterialTheme.typography.titleLarge)
        TextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("Title") },
          modifier = Modifier.fillMaxWidth(),
        )
        TextField(
          value = content,
          onValueChange = { content = it },
          label = { Text("Content") },
          modifier = Modifier.fillMaxWidth(),
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End,
        ) {
          androidx.compose.material3.TextButton(onClick = onDismiss) {
            Text("Cancel")
          }
          androidx.compose.material3.TextButton(
            onClick = { onAdd(title, content) },
            enabled = title.isNotBlank() && content.isNotBlank(),
          ) {
            Text("Add")
          }
        }
      }
    }
  }
}

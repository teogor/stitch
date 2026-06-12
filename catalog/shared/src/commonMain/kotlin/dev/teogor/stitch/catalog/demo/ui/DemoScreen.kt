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

package dev.teogor.stitch.catalog.demo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.teogor.stitch.catalog.demo.domain.model.DemoModel

@Composable
@Suppress("ktlint:standard:function-naming")
fun DemoScreen(
  modifier: Modifier = Modifier,
  viewModel: DemoViewModel = viewModel { DemoViewModel() },
) {
  val items by viewModel.items.collectAsState()

  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp),
  ) {
    Text(
      text = "Room 3 KMP Demo",
      style = MaterialTheme.typography.headlineSmall,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      value = title,
      onValueChange = { title = it },
      label = { Text("Title") },
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = description,
      onValueChange = { description = it },
      label = { Text("Description") },
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        if (title.isNotBlank() && description.isNotBlank()) {
          viewModel.addItem(title, description)
          title = ""
          description = ""
        }
      },
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text("Add Item")
    }

    Spacer(modifier = Modifier.height(24.dp))

    HorizontalDivider()

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      modifier = Modifier.weight(1f),
    ) {
      items(items) { item ->
        DemoItemCard(item)
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
fun DemoItemCard(item: DemoModel) {
  Card(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
    ) {
      Text(
        text = item.title,
        style = MaterialTheme.typography.titleMedium,
      )
      Text(
        text = item.description,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

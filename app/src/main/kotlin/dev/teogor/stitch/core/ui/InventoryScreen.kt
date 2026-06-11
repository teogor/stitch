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

package dev.teogor.stitch.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.stitch.core.database.model.InventoryProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
  val products by viewModel.products.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Stitch Inventory Demo") },
      )
    },
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(16.dp),
    ) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.updateSearchQuery(it) },
        label = { Text("Search Products") },
        modifier = Modifier.fillMaxWidth(),
      )

      Spacer(modifier = Modifier.padding(8.dp))

      AddProductSection(onAddProduct = { name, price ->
        viewModel.addProduct(name, 1L, price, 10) // Simplified for demo
      })

      Spacer(modifier = Modifier.padding(8.dp))

      Text("Products", style = MaterialTheme.typography.titleLarge)
      HorizontalDivider()

      LazyColumn {
        items(products) { product ->
          ProductItem(
            product = product,
            onDelete = { viewModel.deleteProduct(product) },
          )
        }
      }
    }
  }
}

@Composable
fun AddProductSection(onAddProduct: (String, Double) -> Unit) {
  var name by remember { mutableStateOf("") }
  var price by remember { mutableStateOf("") }

  Card(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text("Add New Product", style = MaterialTheme.typography.titleMedium)
      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Product Name") },
        modifier = Modifier.fillMaxWidth(),
      )
      OutlinedTextField(
        value = price,
        onValueChange = { price = it },
        label = { Text("Price") },
        modifier = Modifier.fillMaxWidth(),
      )
      Button(
        onClick = {
          if (name.isNotBlank() && price.toDoubleOrNull() != null) {
            onAddProduct(name, price.toDouble())
            name = ""
            price = ""
          }
        },
        modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
      ) {
        Text("Add Product")
      }
    }
  }
}

@Composable
fun ProductItem(product: InventoryProduct, onDelete: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(product.name, style = MaterialTheme.typography.bodyLarge)
      Text("Price: \$${product.price}", style = MaterialTheme.typography.bodyMedium)
    }
    IconButton(onClick = onDelete) {
      Text("DEL") // Simplified delete button
    }
  }
}

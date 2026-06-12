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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.teogor.stitch.core.database.model.InventoryCategory
import dev.teogor.stitch.core.database.model.InventoryProduct
import dev.teogor.stitch.data.repository.InventoryCategoryRepository
import dev.teogor.stitch.data.repository.InventoryProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(
  private val productRepository: InventoryProductRepository,
  private val categoryRepository: InventoryCategoryRepository,
) : ViewModel() {

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery

  val products: StateFlow<List<InventoryProduct>> = combine(
    productRepository.getAll(),
    _searchQuery,
  ) { products, query ->
    if (query.isBlank()) {
      products
    } else {
      products.filter { it.name.contains(query, ignoreCase = true) }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val categories: StateFlow<List<InventoryCategory>> = categoryRepository.getAll()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  init {
    viewModelScope.launch {
      categoryRepository.getAll().first().let { categories ->
        if (categories.isEmpty()) {
          categoryRepository.insert(InventoryCategory(name = "General"))
        }
      }
    }
  }

  fun updateSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun addProduct(name: String, categoryId: Long, price: Double, quantity: Int) {
    viewModelScope.launch {
      productRepository.insert(
        InventoryProduct(
          name = name,
          categoryId = categoryId,
          price = price,
          quantity = quantity,
        ),
      )
    }
  }

  fun addCategory(name: String) {
    viewModelScope.launch {
      categoryRepository.insert(InventoryCategory(name = name))
    }
  }

  fun deleteProduct(product: InventoryProduct) {
    viewModelScope.launch {
      productRepository.delete(product)
    }
  }
}

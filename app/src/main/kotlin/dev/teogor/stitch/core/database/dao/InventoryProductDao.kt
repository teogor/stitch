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

package dev.teogor.stitch.core.database.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import dev.teogor.stitch.ExplicitEntities
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.core.database.model.InventoryProduct
import kotlinx.coroutines.flow.Flow

@Dao
@ExplicitEntities(
  entities = [
    InventoryProduct::class,
  ],
)
interface InventoryProductDao {
  @RawOperation
  @Query("SELECT * FROM inventory_products")
  fun getAll(): Flow<List<InventoryProduct>>

  @Query("SELECT * FROM inventory_products WHERE id = :id")
  suspend fun getById(id: Long): InventoryProduct?

  @RawOperation
  @Query("SELECT * FROM inventory_products WHERE name LIKE '%' || :query || '%'")
  fun searchProducts(query: String): Flow<List<InventoryProduct>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(product: InventoryProduct): Long

  @Update
  suspend fun update(product: InventoryProduct)

  @Delete
  suspend fun delete(product: InventoryProduct)
}

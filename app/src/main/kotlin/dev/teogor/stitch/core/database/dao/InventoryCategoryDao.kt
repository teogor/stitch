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
import dev.teogor.stitch.core.database.model.InventoryCategory
import kotlinx.coroutines.flow.Flow

@Dao
@ExplicitEntities(
  entities = [
    InventoryCategory::class,
  ],
)
interface InventoryCategoryDao {
  @RawOperation
  @Query("SELECT * FROM inventory_categories")
  fun getAll(): Flow<List<InventoryCategory>>

  @Query("SELECT * FROM inventory_categories WHERE id = :id")
  suspend fun getById(id: Long): InventoryCategory?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(category: InventoryCategory): Long

  @Update
  suspend fun update(category: InventoryCategory)

  @Delete
  suspend fun delete(category: InventoryCategory)
}

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

package dev.teogor.stitch.catalog.demo.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import dev.teogor.stitch.catalog.demo.data.local.entity.DemoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DemoDao {
  @Query("SELECT * FROM demo_table ORDER BY id DESC")
  fun observeAll(): Flow<List<DemoEntity>>

  @Query("SELECT * FROM demo_table WHERE id = :id")
  suspend fun getById(id: Long): DemoEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: DemoEntity)

  @Delete
  suspend fun delete(entity: DemoEntity)
}

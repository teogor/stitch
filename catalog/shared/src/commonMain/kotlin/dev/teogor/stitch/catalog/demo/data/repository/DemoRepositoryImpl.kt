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

package dev.teogor.stitch.catalog.demo.data.repository

import dev.teogor.stitch.catalog.demo.data.local.AppDatabase
import dev.teogor.stitch.catalog.demo.data.local.dao.DemoDao
import dev.teogor.stitch.catalog.demo.data.local.entity.toDomain
import dev.teogor.stitch.catalog.demo.data.local.entity.toEntity
import dev.teogor.stitch.catalog.demo.domain.model.DemoModel
import dev.teogor.stitch.catalog.demo.domain.repository.DemoRepository
import dev.teogor.stitch.runtime.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DemoRepositoryImpl(
  private val db: AppDatabase,
  private val demoDao: DemoDao = db.demoDao(),
) : DemoRepository {

  override fun observeAll(): Flow<List<DemoModel>> {
    return demoDao.observeAll().map { entities ->
      entities.map { it.toDomain() }
    }
  }

  override suspend fun getById(id: Long): DemoModel? {
    return demoDao.getById(id)?.toDomain()
  }

  override suspend fun insert(item: DemoModel) {
    demoDao.insert(item.toEntity())
  }

  override suspend fun bulkInsert(items: List<DemoModel>) {
    db.withTransaction {
      items.forEach { insert(it) }
    }
  }

  override suspend fun delete(item: DemoModel) {
    demoDao.delete(item.toEntity())
  }
}

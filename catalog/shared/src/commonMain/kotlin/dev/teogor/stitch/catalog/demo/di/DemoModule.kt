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

package dev.teogor.stitch.catalog.demo.di

import dev.teogor.stitch.catalog.demo.data.local.AppDatabase
import dev.teogor.stitch.catalog.demo.data.local.getDatabaseBuilder
import dev.teogor.stitch.catalog.demo.data.repository.DemoRepositoryImpl
import dev.teogor.stitch.catalog.demo.domain.repository.DemoRepository
import dev.teogor.stitch.catalog.demo.domain.usecase.GetDemoItemsUseCase
import dev.teogor.stitch.catalog.demo.domain.usecase.SaveDemoItemUseCase
import kotlinx.coroutines.Dispatchers

object DemoModule {
  private val database: AppDatabase by lazy {
    getDatabaseBuilder()
      .setQueryCoroutineContext(Dispatchers.Default)
      .build()
  }

  private val demoDao by lazy { database.demoDao() }

  val demoRepository: DemoRepository by lazy {
    DemoRepositoryImpl(demoDao)
  }

  val getDemoItemsUseCase by lazy {
    GetDemoItemsUseCase(demoRepository)
  }

  val saveDemoItemUseCase by lazy {
    SaveDemoItemUseCase(demoRepository)
  }
}

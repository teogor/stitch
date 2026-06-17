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

package dev.teogor.stitch.catalog.demo.domain.usecase

import dev.teogor.stitch.catalog.demo.domain.model.DemoModel
import dev.teogor.stitch.catalog.demo.domain.repository.DemoRepository

class SaveDemoItemUseCase(private val repository: DemoRepository) {
    suspend operator fun invoke(item: DemoModel) {
        if (item.title.isBlank()) {
            throw IllegalArgumentException("Title cannot be empty")
        }
        repository.insert(item)
    }
}

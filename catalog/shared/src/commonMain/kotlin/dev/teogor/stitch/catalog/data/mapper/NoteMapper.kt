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

package dev.teogor.stitch.catalog.data.mapper

import dev.teogor.stitch.catalog.data.database.NoteEntity
import dev.teogor.stitch.catalog.domain.model.NoteModel

class NoteMapper {
  fun toDomain(entity: NoteEntity) = NoteModel(
    id = entity.id,
    title = entity.title,
    content = entity.content,
  )

  fun toEntity(model: NoteModel) = NoteEntity(
    id = model.id,
    title = model.title,
    content = model.content,
  )
}

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

@file:Suppress("ObjectPropertyName")

package dev.teogor.stitch.codegen

import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.DatabaseModel
import dev.teogor.stitch.codegen.model.RoomModel
import dev.teogor.stitch.codegen.writers.DatabaseBuilderOutputWriter
import dev.teogor.stitch.codegen.writers.DatabaseConstructorOutputWriter
import dev.teogor.stitch.codegen.writers.OperationOutputWriter
import dev.teogor.stitch.codegen.writers.RepositoryImplOutputWriter
import dev.teogor.stitch.codegen.writers.RepositoryOutputWriter
import dev.teogor.stitch.codegen.writers.StitchModuleOutputWriter

class CodeGenerator(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  private val codeGenConfig: CodeGenConfig,
) {

  private val repositoryOutputWriter = RepositoryOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  private val repositoryImplOutputWriter = RepositoryImplOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  private val operationOutputWriter = OperationOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  private val stitchModuleOutputWriter = StitchModuleOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  private val databaseConstructorOutputWriter = DatabaseConstructorOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  private val databaseBuilderOutputWriter = DatabaseBuilderOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

  fun generate(databaseModels: Sequence<DatabaseModel>, roomModels: List<RoomModel>) {
    databaseConstructorOutputWriter.write(databaseModels)
    databaseBuilderOutputWriter.write(databaseModels)
    roomModels.filter { it.hasDao }.forEach { roomModel ->
      val repositoryType = repositoryOutputWriter.write(roomModel)
      if (codeGenConfig.enableRepositoryImplGeneration) {
        val database = databaseModels.firstOrNull {
          it.entities.contains(roomModel.entity) || it.views.contains(roomModel.entity)
        } ?: databaseModels.firstOrNull()
        repositoryImplOutputWriter.write(roomModel, repositoryType, database?.type)
      }
    }

    stitchModuleOutputWriter.write(databaseModels, roomModels)

    if (codeGenConfig.enableOperationGeneration) {
      operationOutputWriter.write(databaseModels, roomModels)
    }
  }
}

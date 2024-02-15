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
import dev.teogor.stitch.codegen.servicelocator.ServiceLocatorAccessor
import dev.teogor.stitch.codegen.servicelocator.operationOutputWriter
import dev.teogor.stitch.codegen.servicelocator.repositoryImplOutputWriter
import dev.teogor.stitch.codegen.servicelocator.repositoryOutputWriter
import dev.teogor.stitch.codegen.servicelocator.stitchModuleOutputWriter

class CodeGenerator(
  override val codeOutputStreamMaker: CodeOutputStreamMaker,
  override val codeGenConfig: CodeGenConfig,
) : ServiceLocatorAccessor {

  fun generate(
    databaseModels: Sequence<DatabaseModel>,
    roomModels: List<RoomModel>,
  ) {
    roomModels.forEach { roomModel ->
      val repositoryType = repositoryOutputWriter.write(roomModel)
      repositoryImplOutputWriter.write(roomModel, repositoryType)
    }

    stitchModuleOutputWriter.write(databaseModels, roomModels)

    if (codeGenConfig.generateOperations) {
      operationOutputWriter.write(databaseModels, roomModels)
    }
  }
}

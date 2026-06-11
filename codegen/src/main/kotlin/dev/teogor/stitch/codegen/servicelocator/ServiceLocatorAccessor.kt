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

package dev.teogor.stitch.codegen.servicelocator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.teogor.stitch.codegen.commons.titleCase
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.RoomModel
import dev.teogor.stitch.codegen.writers.OperationOutputWriter
import dev.teogor.stitch.codegen.writers.RepositoryImplOutputWriter
import dev.teogor.stitch.codegen.writers.RepositoryOutputWriter
import dev.teogor.stitch.codegen.writers.StitchModuleOutputWriter

internal interface ServiceLocatorAccessor {
  val codeOutputStreamMaker: CodeOutputStreamMaker
  val codeGenConfig: CodeGenConfig
}

abstract class OutputWriter(
  internal val codeGenConfig: CodeGenConfig,
) {

  fun RoomModel.getBasePackage() = codeGenConfig.generatedPackageName ?: packageName

  fun RoomModel.getRepositoryPackage() = codeGenConfig.repositoryPackage
    ?: "${getBasePackage()}.data.repository"

  fun RoomModel.getRepositoryImplPackage() = codeGenConfig.repositoryImplPackage
    ?: "${getRepositoryPackage()}.impl"

  fun RoomModel.getOperationPackage() = codeGenConfig.operationPackage
    ?: "${getBasePackage()}.database.operation"

  fun RoomModel.getDiPackage() = codeGenConfig.diPackage
    ?: "${getBasePackage()}.di"

  fun RoomModel.getRepositoryName() = "$name${codeGenConfig.repositorySuffix}"

  fun RoomModel.getRepositoryImplName() = "${getRepositoryName()}Impl"

  fun RoomModel.getOperationName(baseName: String) =
    "$name${baseName.titleCase()}${codeGenConfig.operationSuffix}"

  fun FunSpec.Builder.addDocumentation(format: String, vararg args: Any) = this.apply {
    if (codeGenConfig.addDocumentation) {
      addKdoc(format, args)
    }
  }

  fun FunSpec.Builder.addDocumentation(block: CodeBlock) = this.apply {
    if (codeGenConfig.addDocumentation) {
      addKdoc(block)
    }
  }

  fun TypeSpec.Builder.addDocumentation(format: String, vararg args: Any) = this.apply {
    if (codeGenConfig.addDocumentation) {
      addKdoc(format, args)
    }
  }

  fun TypeSpec.Builder.addDocumentation(block: CodeBlock) = this.apply {
    if (codeGenConfig.addDocumentation) {
      addKdoc(block)
    }
  }
}

internal val ServiceLocatorAccessor.repositoryOutputWriter
  get() = RepositoryOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

internal val ServiceLocatorAccessor.repositoryImplOutputWriter
  get() = RepositoryImplOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

internal val ServiceLocatorAccessor.operationOutputWriter
  get() = OperationOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

internal val ServiceLocatorAccessor.stitchModuleOutputWriter
  get() = StitchModuleOutputWriter(
    codeOutputStreamMaker,
    codeGenConfig,
  )

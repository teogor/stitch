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

package dev.teogor.stitch.codegen.writers

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import dev.teogor.stitch.codegen.commons.fileBuilder
import dev.teogor.stitch.codegen.commons.mapTo
import dev.teogor.stitch.codegen.commons.shortName
import dev.teogor.stitch.codegen.commons.writeWith
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.RoomModel

class RepositoryOutputWriter(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  codeGenConfig: CodeGenConfig,
) : OutputWriter(codeGenConfig) {

  fun write(roomModel: RoomModel): TypeName {
    val repositoryName = roomModel.getRepositoryName()
    val repositoryPackage = roomModel.getRepositoryPackage()
    fileBuilder(
      packageName = repositoryPackage,
      fileName = repositoryName,
    ) {
      addType(
        TypeSpec.interfaceBuilder(repositoryName)
          .addDocumentation(
            """
            Interface for accessing and managing [${roomModel.name}] data.

            This repository provides a high-level abstraction for interacting with [${roomModel.name}]'s,
            managing CRUD operations and data flow.

            Generated based on [${roomModel.dao!!.shortName}]

            @see [${roomModel.name}]
            @see [${roomModel.dao.shortName}]
            """.trimIndent(),
          )
          .apply {
            roomModel.functions.forEach { function ->
              val returnType = function.returnType.mapTo(roomModel)
              addFunction(
                FunSpec.builder(function.name)
                  .addModifiers(KModifier.ABSTRACT)
                  .apply {
                    val kdoc = buildString {
                      appendLine(
                        "Performs the ${function.name} operation on [${roomModel.name}]s.",
                      )

                      if (function.isSuspend) {
                        appendLine()
                        appendLine(
                          "This function is executed asynchronously and might block the calling thread.",
                        )
                        appendLine(
                          "Use it within coroutines or with appropriate thread management.",
                        )
                      }

                      if (function.isTransaction) {
                        appendLine()
                        appendLine(
                          "Note: This operation is executed within a database transaction.",
                        )
                      }

                      if (function.parameters.isNotEmpty()) {
                        appendLine()
                        appendLine(
                          function.parameters.joinToString(
                            separator = "\n",
                          ) { "@param ${it.name}" },
                        )
                      }

                      if (returnType != UNIT) {
                        appendLine()
                        appendLine(
                          "@return ${returnType.shortName}",
                        )
                      }
                    }

                    addDocumentation(kdoc.trimIndent())
                  }
                  .apply {
                    if (returnType != UNIT) {
                      returns(returnType)
                    }
                    function.parameters.forEach { parameter ->
                      addParameter(parameter.name, parameter.type.mapTo(roomModel))
                    }
                    if (function.isSuspend) {
                      addModifiers(KModifier.SUSPEND)
                    }
                  }
                  .build(),
              )
            }
          }
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)

    return ClassName(
      repositoryPackage,
      repositoryName,
    )
  }
}

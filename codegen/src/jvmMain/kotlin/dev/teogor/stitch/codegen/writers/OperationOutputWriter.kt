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
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import dev.teogor.stitch.Operation
import dev.teogor.stitch.OperationSignature
import dev.teogor.stitch.api.DiFramework
import dev.teogor.stitch.api.OperationGenerationLevel
import dev.teogor.stitch.codegen.commons.JAVAX_INJECT
import dev.teogor.stitch.codegen.commons.METRO_INJECT
import dev.teogor.stitch.codegen.commons.fileBuilder
import dev.teogor.stitch.codegen.commons.writeWith
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.DatabaseModel
import dev.teogor.stitch.codegen.model.RoomModel

class OperationOutputWriter(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  codeGenConfig: CodeGenConfig,
) : OutputWriter(codeGenConfig) {

  fun write(databaseModels: Sequence<DatabaseModel>, roomModels: List<RoomModel>) {
    roomModels.forEach { room ->
      val generatedClasses = mutableMapOf<String, MutableList<FunSpec>>()

      val filteredFunctions = when (codeGenConfig.operationGenerationLevel) {
        OperationGenerationLevel.ALL -> room.functions
        OperationGenerationLevel.EXPLICIT -> room.functions.filter {
          it.enableRawOperationGeneration
        }

        OperationGenerationLevel.AUTOMATIC -> room.functions.filter {
          it.enableRawOperationGeneration
        }

        OperationGenerationLevel.DISABLED -> emptyList()
      }
      filteredFunctions.forEach { function ->
        val existingFunctions = generatedClasses.getOrDefault(
          function.name,
          mutableListOf(),
        )
        val args = function.parameters.joinToString(separator = ",") {
          it.name
        }

        existingFunctions.add(
          FunSpec.builder("invoke")
            .apply {
              addAnnotation(OperationSignature::class)
              if (function.isSuspend) {
                addModifiers(KModifier.SUSPEND)
              }
              function.parameters.forEach { parameter ->
                addParameter(parameter.name, parameter.type)
              }
              if (function.returnType != UNIT) {
                returns(function.returnType)
              }
            }
            .addModifiers(KModifier.OPERATOR)
            .addStatement("return repository.${function.name}($args)")
            .build(),
        )

        generatedClasses[function.name] = existingFunctions
      }

      generatedClasses.forEach { (baseName, invokeFunctions) ->
        val className = room.getOperationName(baseName)
        val operationPackage = room.getOperationPackage()
        val repositoryPackage = room.getRepositoryPackage()
        val repositoryName = room.getRepositoryName()
        fileBuilder(
          packageName = operationPackage,
          fileName = className,
        ) {
          addType(
            TypeSpec.classBuilder(className)
              .apply {
                addAnnotation(Operation::class)
                addProperty(
                  PropertySpec.builder(
                    "repository",
                    ClassName(
                      repositoryPackage,
                      repositoryName,
                    ),
                  )
                    .initializer("repository")
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
                )
                primaryConstructor(
                  FunSpec.constructorBuilder()
                    .addParameter(
                      "repository",
                      ClassName(
                        repositoryPackage,
                        repositoryName,
                      ),
                    )
                    .apply {
                      val injectAnnotation = when (codeGenConfig.diFramework) {
                        DiFramework.METRO -> METRO_INJECT
                        DiFramework.HILT, DiFramework.DAGGER -> JAVAX_INJECT
                        DiFramework.CUSTOM -> codeGenConfig.injectAnnotation?.let {
                          ClassName.bestGuess(it)
                        }

                        else -> null
                      }
                      if (injectAnnotation != null) {
                        addAnnotation(injectAnnotation)
                      }
                    }
                    .build(),
                )
                invokeFunctions.forEach { addFunction(it) }
              }
              .build(),
          )
        }.writeWith(codeOutputStreamMaker) {
          // todo error
          val message = buildString {
            appendLine(room)
            appendLine()
            appendLine(className)
            appendLine()
            appendLine(invokeFunctions.joinToString(separator = ", "))
          }
          throw RuntimeException(message)
        }
      }
    }
  }
}

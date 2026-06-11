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

@file:Suppress("SameParameterValue")

package dev.teogor.stitch.ksp.processors

import dev.teogor.stitch.api.OperationGenerationLevel
import dev.teogor.stitch.codegen.model.CodeGenConfig

class ConfigParser(
  private val options: Map<String, String>,
) {

  companion object {
    private const val PREFIX = "stitch"

    // Configs
    private const val ADD_DOCUMENTATION = "$PREFIX.addDocumentation"
    private const val ENABLE_OPERATION_GENERATION = "$PREFIX.enableOperationGeneration"
    private const val GENERATED_PACKAGE_NAME = "$PREFIX.generatedPackageName"
    private const val OPERATION_GENERATION_LEVEL = "$PREFIX.operationGenerationLevel"
    private const val REPOSITORY_SUFFIX = "$PREFIX.repositorySuffix"
    private const val OPERATION_SUFFIX = "$PREFIX.operationSuffix"
    private const val REPOSITORY_PACKAGE = "$PREFIX.repositoryPackage"
    private const val REPOSITORY_IMPL_PACKAGE = "$PREFIX.repositoryImplPackage"
    private const val OPERATION_PACKAGE = "$PREFIX.operationPackage"
    private const val DI_PACKAGE = "$PREFIX.diPackage"
  }

  fun parse(): CodeGenConfig {
    val addDocumentation = parseBoolean(ADD_DOCUMENTATION) ?: true
    val enableOperationGeneration = parseBoolean(ENABLE_OPERATION_GENERATION) ?: true
    val generatedPackageName = options[GENERATED_PACKAGE_NAME]?.trim()?.removeSuffix(".")
    val operationGenerationLevel = getOperationGenerationLevel()
    val repositorySuffix = options[REPOSITORY_SUFFIX]?.trim() ?: "Repository"
    val operationSuffix = options[OPERATION_SUFFIX]?.trim() ?: "Operation"
    val repositoryPackage = options[REPOSITORY_PACKAGE]?.trim()
    val repositoryImplPackage = options[REPOSITORY_IMPL_PACKAGE]?.trim()
    val operationPackage = options[OPERATION_PACKAGE]?.trim()
    val diPackage = options[DI_PACKAGE]?.trim()

    return CodeGenConfig(
      addDocumentation = addDocumentation,
      enableOperationGeneration = enableOperationGeneration,
      generatedPackageName = generatedPackageName,
      operationGenerationLevel = operationGenerationLevel,
      repositorySuffix = repositorySuffix,
      operationSuffix = operationSuffix,
      repositoryPackage = repositoryPackage,
      repositoryImplPackage = repositoryImplPackage,
      operationPackage = operationPackage,
      diPackage = diPackage,
    )
  }

  private fun getOperationGenerationLevel(): OperationGenerationLevel {
    val stringValue = options[OPERATION_GENERATION_LEVEL]?.trim()
    stringValue ?: return OperationGenerationLevel.EXPLICIT

    return OperationGenerationLevel.from(stringValue)
  }

  private fun parseBoolean(key: String): Boolean? {
    return options[key]?.runCatching {
      toBooleanStrict()
    }?.getOrElse {
      throw WrongConfigurationSetup("$key must be a boolean value!", cause = it)
    }
  }
}

class WrongConfigurationSetup(message: String, cause: Throwable? = null) :
  RuntimeException(message, cause)

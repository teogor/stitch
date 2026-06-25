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

package dev.teogor.stitch

import dev.teogor.stitch.api.DiFramework
import dev.teogor.stitch.api.OperationGenerationLevel
import dev.teogor.stitch.api.StitchExtension
import dev.teogor.stitch.api.Visibility
import org.gradle.process.CommandLineArgumentProvider

class StitchSchemaArgProvider(
    private val addDocumentation: Boolean,
    private val enableOperationGeneration: Boolean,
    private val generatedPackageName: String?,
    private val operationGenerationLevel: OperationGenerationLevel,
    private val repositorySuffix: String,
    private val operationSuffix: String,
    private val repositoryPackage: String?,
    private val repositoryImplPackage: String?,
    private val operationPackage: String?,
    private val diPackage: String?,
    private val enableMetro: Boolean,
    private val diFramework: DiFramework,
    private val injectAnnotation: String?,
    private val repositoryBaseClass: String?,
    private val visibility: Visibility,
    private val enableRepositoryImplGeneration: Boolean,
    private val enableDatabaseBuilderGeneration: Boolean,
) : CommandLineArgumentProvider {

    override fun asArguments() = listOf(
        "stitch.addDocumentation" to addDocumentation,
        "stitch.enableOperationGeneration" to enableOperationGeneration,
        "stitch.generatedPackageName" to generatedPackageName,
        "stitch.operationGenerationLevel" to operationGenerationLevel,
        "stitch.repositorySuffix" to repositorySuffix,
        "stitch.operationSuffix" to operationSuffix,
        "stitch.enableMetro" to enableMetro,
        "stitch.diFramework" to diFramework,
        "stitch.visibility" to visibility,
        "stitch.enableRepositoryImplGeneration" to enableRepositoryImplGeneration,
        "stitch.enableDatabaseBuilderGeneration" to enableDatabaseBuilderGeneration,
        "stitch.repositoryPackage" to repositoryPackage,
        "stitch.repositoryImplPackage" to repositoryImplPackage,
        "stitch.operationPackage" to operationPackage,
        "stitch.diPackage" to diPackage,
        "stitch.injectAnnotation" to injectAnnotation,
        "stitch.repositoryBaseClass" to repositoryBaseClass,
    ).mapNotNull { (key, value) ->
        val valueStr = value?.toString()
        if (!valueStr.isNullOrBlank()) "$key=$valueStr" else null
    }

    companion object {
        @Suppress("DEPRECATION")
        fun from(stitchExtension: StitchExtension) = StitchSchemaArgProvider(
            addDocumentation = stitchExtension.addDocumentation,
            enableOperationGeneration = stitchExtension.enableOperationGeneration,
            generatedPackageName = stitchExtension.generatedPackageName,
            operationGenerationLevel = stitchExtension.operationGenerationLevel,
            repositorySuffix = stitchExtension.repositorySuffix,
            operationSuffix = stitchExtension.operationSuffix,
            repositoryPackage = stitchExtension.repositoryPackage,
            repositoryImplPackage = stitchExtension.repositoryImplPackage,
            operationPackage = stitchExtension.operationPackage,
            diPackage = stitchExtension.diPackage,
            enableMetro = stitchExtension.enableMetro,
            diFramework = stitchExtension.diFramework,
            injectAnnotation = stitchExtension.injectAnnotation,
            repositoryBaseClass = stitchExtension.repositoryBaseClass,
            visibility = stitchExtension.visibility,
            enableRepositoryImplGeneration = stitchExtension.enableRepositoryImplGeneration,
            enableDatabaseBuilderGeneration = stitchExtension.enableDatabaseBuilderGeneration,
        )
    }
}

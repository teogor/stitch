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

package dev.teogor.stitch.ksp.processors

import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.DatabaseView
import androidx.room3.Entity
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import dev.teogor.stitch.MapTo
import dev.teogor.stitch.StitchName
import dev.teogor.stitch.codegen.CodeGenerator
import dev.teogor.stitch.codegen.facades.Logger
import dev.teogor.stitch.ksp.codegen.KspCodeOutputStreamMaker
import dev.teogor.stitch.ksp.codegen.KspLogger
import dev.teogor.stitch.ksp.data.config.ConfigParser
import dev.teogor.stitch.ksp.mappers.DatabaseModelMapper
import dev.teogor.stitch.ksp.mappers.KspToCodeGenDestinationsMapper
import dev.teogor.stitch.ksp.mappers.RoomModelMapper
import kotlin.reflect.KClass

class StitchProcessor(
    private val codeGenerator: KSPCodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>,
) : SymbolProcessor {

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        Logger.instance = KspLogger(logger)

        val annotatedDao = resolver.getDao()
        val annotatedEntities = resolver.getEntities()
        val annotatedViews = resolver.getViews()
        val annotatedDatabases = resolver.getDatabases()

        if (
            !annotatedDao.iterator().hasNext() &&
            !annotatedEntities.iterator().hasNext() &&
            !annotatedViews.iterator().hasNext() &&
            !annotatedDatabases.iterator().hasNext()
        ) {
            return emptyList()
        }

        if (!validate(resolver)) {
            return emptyList()
        }

        val databaseModelMapper = DatabaseModelMapper()
        val databaseModels = annotatedDatabases.map { database ->
            databaseModelMapper.map(database)
        }

        val roomModelMapper = RoomModelMapper(annotatedDao)
        val roomModels = (annotatedEntities + annotatedViews)
            .mapNotNull { entity ->
                roomModelMapper.map(entity)
            }
            .toList()
            .distinctBy { it.dao }

        CodeGenerator(
            codeOutputStreamMaker = KspCodeOutputStreamMaker(
                codeGenerator = codeGenerator,
                sourceMapper = KspToCodeGenDestinationsMapper(resolver),
            ),
            codeGenConfig = ConfigParser(options).parse(),
        ).generate(
            databaseModels = databaseModels,
            roomModels = roomModels,
        )

        return emptyList()
    }

    private fun validate(resolver: Resolver): Boolean {
        var isValid = true

        // Validate @MapTo usage
        resolver.getSymbolsWithAnnotation(MapTo::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
            .forEach { classDecl ->
                val hasEntity = classDecl.annotations.any {
                    it.shortName.asString() == Entity::class.simpleName
                }
                if (!hasEntity) {
                    logger.error(
                        "@MapTo can only be applied to Room @Entity classes.",
                        classDecl,
                    )
                    isValid = false
                }
            }

        // Validate @StitchName usage
        resolver.getSymbolsWithAnnotation(StitchName::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
            .forEach { classDecl ->
                val hasDao = classDecl.annotations.any {
                    it.shortName.asString() == Dao::class.simpleName
                }
                if (!hasDao) {
                    logger.error(
                        "@StitchName can only be applied to Room @Dao interfaces or classes.",
                        classDecl,
                    )
                    isValid = false
                }
            }

        return isValid
    }

    private fun Resolver.findAnnotations(kClass: KClass<*>) = getSymbolsWithAnnotation(
        kClass.qualifiedName.toString(),
    )

    private fun Resolver.getDao(): Sequence<KSClassDeclaration> {
        return findAnnotations(Dao::class).filterIsInstance<KSClassDeclaration>()
    }

    private fun Resolver.getEntities(): Sequence<KSClassDeclaration> {
        return findAnnotations(Entity::class).filterIsInstance<KSClassDeclaration>()
    }

    private fun Resolver.getViews(): Sequence<KSClassDeclaration> {
        return findAnnotations(DatabaseView::class).filterIsInstance<KSClassDeclaration>()
    }

    private fun Resolver.getDatabases(): Sequence<KSClassDeclaration> {
        return findAnnotations(Database::class).filterIsInstance<KSClassDeclaration>()
    }
}

typealias KSPClassKind = com.google.devtools.ksp.symbol.ClassKind
typealias KSPCodeGenerator = com.google.devtools.ksp.processing.CodeGenerator

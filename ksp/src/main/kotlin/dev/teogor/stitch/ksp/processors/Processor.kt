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
import androidx.room3.Delete
import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.RawQuery
import androidx.room3.Relation
import androidx.room3.Transaction
import androidx.room3.Update
import androidx.room3.Upsert
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.teogor.stitch.ExplicitEntities
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.codegen.CodeGenerator
import dev.teogor.stitch.codegen.commons.findCommonBase
import dev.teogor.stitch.codegen.commons.getCommonBase
import dev.teogor.stitch.codegen.facades.Logger
import dev.teogor.stitch.codegen.model.DatabaseModel
import dev.teogor.stitch.codegen.model.FieldKind
import dev.teogor.stitch.codegen.model.FunctionKind
import dev.teogor.stitch.codegen.model.OperationType
import dev.teogor.stitch.codegen.model.ParameterKind
import dev.teogor.stitch.codegen.model.RoomModel
import dev.teogor.stitch.ksp.codegen.KspCodeOutputStreamMaker
import dev.teogor.stitch.ksp.codegen.KspLogger
import dev.teogor.stitch.ksp.commons.findArgumentValue
import dev.teogor.stitch.ksp.commons.firstAnnotation
import kotlin.reflect.KClass

class Processor(
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

    val databaseModels = annotatedDatabases.map { database ->
      val annotation = database.annotations.find {
        it.shortName.asString() == Database::class.simpleName
      }!!
      val entities = (
        annotation.arguments.find {
          it.name!!.getShortName() == "entities"
        }?.value as? List<KSType>
        )?.map {
        (it.declaration as KSClassDeclaration).toClassName()
      } ?: emptyList()

      val views = (
        annotation.arguments.find {
          it.name!!.getShortName() == "views"
        }?.value as? List<KSType>
        )?.map {
        (it.declaration as KSClassDeclaration).toClassName()
      } ?: emptyList()

      val functions = database.getDeclaredFunctions().toList().map { function ->
        val fieldName = function.simpleName.asString()
        val fieldType = function.returnType?.resolve().let {
          it?.toTypeName() ?: UNIT
        }
        val parameters = function.parameters.map { parameter ->
          ParameterKind(
            name = parameter.toString(),
            type = parameter.type.toTypeName(),
          )
        }
        val isSuspend = function.modifiers.contains(Modifier.SUSPEND)
        FunctionKind(
          name = fieldName,
          returnType = fieldType,
          parameters = parameters,
          isSuspend = isSuspend,
        )
      }
      DatabaseModel(
        entities = entities,
        views = views,
        type = database.toClassName(),
        functions = functions,
      )
    }

    val roomModels = (annotatedEntities + annotatedViews)
      .toList()
      .filter {
        annotatedDao.firstOrNull {
          it.simpleName.asString().startsWith(it.simpleName.asString())
        } != null
      }
      .map { entity ->
        val daoToEntitiesMap = mutableMapOf<KSClassDeclaration, List<KSType>>()
        annotatedDao.forEach { daoClass ->
          val explicitEntities = daoClass.firstAnnotation<ExplicitEntities>()
          if (explicitEntities != null) {
            val entities = explicitEntities
              .findArgumentValue<ArrayList<KSType>>("entities")
              ?.toList()
            daoToEntitiesMap[daoClass] = entities ?: emptyList()
          }
        }
        val matchingDaoKeys = daoToEntitiesMap.keys.filter { daoClass ->
          daoToEntitiesMap[daoClass]?.any { entityTest ->
            entityTest.declaration.closestClassDeclaration() == entity
          } ?: false
        }

        val matchingEntityClass = matchingDaoKeys.firstOrNull()

        val potentialDao = annotatedDao.firstOrNull {
          it.simpleName.asString().startsWith(
            entity.simpleName.asString(),
          )
        }
        val dao = when {
          matchingEntityClass != null -> {
            matchingEntityClass
          }

          potentialDao != null -> potentialDao
          else -> null
        }
        Pair(
          entity,
          dao,
        )
      }
      // todo better handling for multiple DAOs
      .distinctBy { it.second }
      .map { (entity, dao) ->
        val fields = entity.primaryConstructor?.parameters?.map { parameter ->
          val fieldName = parameter.name!!.asString()
          val fieldType = parameter.type.resolve()
          FieldKind(
            name = fieldName,
            type = ClassName(
              fieldType.declaration.packageName.asString(),
              fieldType.declaration.simpleName.asString(),
            ),
            isEmbedded = parameter.isAnnotationPresent(Embedded::class),
            isRelation = parameter.isAnnotationPresent(Relation::class),
          )
        } ?: emptyList()

        val functions = dao?.getDeclaredFunctions()?.toList()?.map { function ->
          val rawOperation = function.getAnnotationsByType(RawOperation::class)
            .firstOrNull()
          val fieldName = function.simpleName.asString()
          val fieldType = function.returnType?.resolve().let {
            it?.toTypeName() ?: UNIT
          }
          val parameters = function.parameters.map { parameter ->
            ParameterKind(
              name = parameter.toString(),
              type = parameter.type.toTypeName(),
            )
          }
          val isSuspend = function.modifiers.contains(Modifier.SUSPEND)

          val operationType = when {
            function.isAnnotationPresent(Query::class) -> OperationType.QUERY
            function.isAnnotationPresent(Insert::class) -> OperationType.INSERT
            function.isAnnotationPresent(Update::class) -> OperationType.UPDATE
            function.isAnnotationPresent(Delete::class) -> OperationType.DELETE
            function.isAnnotationPresent(Upsert::class) -> OperationType.UPSERT
            function.isAnnotationPresent(RawQuery::class) -> OperationType.RAW_QUERY
            else -> OperationType.QUERY
          }

          FunctionKind(
            name = fieldName,
            returnType = fieldType,
            parameters = parameters,
            isSuspend = isSuspend,
            operationType = operationType,
            isTransaction = function.isAnnotationPresent(Transaction::class),
            enableRawOperationGeneration = rawOperation?.generate ?: false,
          )
        } ?: emptyList()
        RoomModel(
          name = getCommonBase(
            entity.simpleName.asString(),
            dao?.simpleName?.asString() ?: "",
          ),
          packageName = findCommonBase(
            string1 = entity.packageName.asString(),
            string2 = dao?.packageName?.asString() ?: entity.packageName.asString(),
          ),
          fields = fields,
          functions = functions,
          entity = entity.toClassName(),
          dao = dao?.toClassName(),
        )
      }

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

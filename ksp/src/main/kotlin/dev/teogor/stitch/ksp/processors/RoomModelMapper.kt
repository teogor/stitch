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

import androidx.room3.Delete
import androidx.room3.Embedded
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.RawQuery
import androidx.room3.Relation
import androidx.room3.Transaction
import androidx.room3.Update
import androidx.room3.Upsert
import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.teogor.stitch.ExplicitEntities
import dev.teogor.stitch.RawOperation
import dev.teogor.stitch.codegen.commons.findCommonBase
import dev.teogor.stitch.codegen.commons.getCommonBase
import dev.teogor.stitch.codegen.model.FieldKind
import dev.teogor.stitch.codegen.model.FunctionKind
import dev.teogor.stitch.codegen.model.OperationType
import dev.teogor.stitch.codegen.model.ParameterKind
import dev.teogor.stitch.codegen.model.RoomModel
import dev.teogor.stitch.ksp.commons.findArgumentValue
import dev.teogor.stitch.ksp.commons.firstAnnotation

class RoomModelMapper(
  private val annotatedDao: Sequence<KSClassDeclaration>,
) {

  fun map(entity: KSClassDeclaration): RoomModel? {
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

    if (dao == null) return null

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

    val functions = dao.getDeclaredFunctions().toList().map { function ->
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
    }

    return RoomModel(
      name = getCommonBase(
        entity.simpleName.asString(),
        dao.simpleName.asString(),
      ),
      packageName = findCommonBase(
        string1 = entity.packageName.asString(),
        string2 = dao.packageName.asString(),
      ),
      fields = fields,
      functions = functions,
      entity = entity.toClassName(),
      dao = dao.toClassName(),
    )
  }
}

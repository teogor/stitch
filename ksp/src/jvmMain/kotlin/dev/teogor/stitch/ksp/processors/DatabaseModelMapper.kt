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

import androidx.room3.Database
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.teogor.stitch.codegen.model.DatabaseModel
import dev.teogor.stitch.codegen.model.FunctionKind
import dev.teogor.stitch.codegen.model.ParameterKind

class DatabaseModelMapper {

  fun map(database: KSClassDeclaration): DatabaseModel {
    val annotation = database.annotations.find {
      it.shortName.asString() == Database::class.simpleName
    }!!
    val entities = (
      annotation.arguments.find {
        it.name!!.getShortName() == "entities"
      }?.value as? List<*>
      )?.filterIsInstance<KSType>()?.map {
      (it.declaration as KSClassDeclaration).toClassName()
    } ?: emptyList()

    val views = (
      annotation.arguments.find {
        it.name!!.getShortName() == "views"
      }?.value as? List<*>
      )?.filterIsInstance<KSType>()?.map {
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
    return DatabaseModel(
      entities = entities,
      views = views,
      type = database.toClassName(),
      functions = functions,
    )
  }
}

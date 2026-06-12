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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import dev.teogor.stitch.codegen.commons.fileBuilder
import dev.teogor.stitch.codegen.commons.writeWith
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.DatabaseModel

class DatabaseConstructorOutputWriter(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  codeGenConfig: CodeGenConfig,
) : OutputWriter(codeGenConfig) {

  fun write(databaseModels: Sequence<DatabaseModel>) {
    if (!codeGenConfig.enableKmpSupport) {
      return
    }

    databaseModels.forEach { databaseModel ->
      val databaseType = databaseModel.type as ClassName
      val packageName = databaseType.packageName
      val databaseName = databaseType.simpleName
      val constructorName = "${databaseName}Constructor"

      fileBuilder(
        packageName = packageName,
        fileName = constructorName,
      ) {
        addType(
          TypeSpec.objectBuilder(constructorName)
            .addModifiers(KModifier.EXPECT)
            .addAnnotation(
              AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "KotlinNoActualForExpect")
                .build(),
            )
            .addSuperinterface(
              ClassName("androidx.room3", "RoomDatabaseConstructor")
                .parameterizedBy(databaseType),
            )
            .addFunction(
              FunSpec.builder("initialize")
                .addModifiers(KModifier.OVERRIDE)
                .returns(databaseType)
                .build(),
            )
            .build(),
        )
      }.writeWith(codeOutputStreamMaker)
    }
  }
}

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
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.teogor.stitch.codegen.commons.fileBuilder
import dev.teogor.stitch.codegen.commons.writeWith
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.DatabaseModel

class DatabaseBuilderOutputWriter(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  codeGenConfig: CodeGenConfig,
) : OutputWriter(codeGenConfig) {

  fun write(databaseModels: Sequence<DatabaseModel>) {
    if (!codeGenConfig.enableKmpSupport) {
      return
    }

    databaseModels.forEach { databaseModel ->
      if (databaseModel.dbFileName == null) return@forEach

      val databaseType = databaseModel.type as ClassName
      val packageName = databaseType.packageName
      val databaseName = databaseType.simpleName
      val fileName = "DatabaseBuilder"

      // commonMain
      writeExpect(packageName, fileName, databaseType)

      // androidMain
      writeAndroidActual(packageName, fileName, databaseType, databaseModel.dbFileName)

      // jvmMain
      writeJvmActual(packageName, fileName, databaseType, databaseModel.dbFileName)

      // iosMain
      writeIosActual(packageName, fileName, databaseType, databaseModel.dbFileName)

      // webMain
      writeWebActual(packageName, fileName, databaseType, databaseModel.dbFileName)
    }
  }

  private fun writeExpect(packageName: String, fileName: String, databaseType: ClassName) {
    fileBuilder(packageName, fileName) {
      addFunction(
        FunSpec.builder("getDatabaseBuilder")
          .addModifiers(KModifier.EXPECT)
          .returns(
            ClassName("androidx.room3", "RoomDatabase", "Builder")
              .parameterizedBy(databaseType),
          )
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }

  private fun writeAndroidActual(
    packageName: String,
    fileName: String,
    databaseType: ClassName,
    dbFileName: String,
  ) {
    fileBuilder(packageName, fileName) {
      addType(
        TypeSpec.objectBuilder("StitchInitializer")
          .addProperty(
            PropertySpec.builder(
              "context",
              ClassName("android.content", "Context").copy(nullable = true),
            )
              .mutable()
              .initializer("null")
              .addModifiers(KModifier.INTERNAL)
              .build(),
          )
          .addFunction(
            FunSpec.builder("initialize")
              .addParameter("context", ClassName("android.content", "Context"))
              .addStatement("this.context = context.applicationContext")
              .build(),
          )
          .build(),
      )

      addFunction(
        FunSpec.builder("getDatabaseBuilder")
          .addModifiers(KModifier.ACTUAL)
          .returns(
            ClassName("androidx.room3", "RoomDatabase", "Builder")
              .parameterizedBy(databaseType),
          )
          .addStatement(
            "val context = StitchInitializer.context ?: throw %T(%S)",
            ClassName("kotlin", "IllegalStateException"),
            "Stitch has not been initialized. Call StitchInitializer.initialize(context) in your Application class.",
          )
          .addStatement(
            "val dbFile = context.getDatabasePath(%S)",
            dbFileName,
          )
          .addStatement(
            "return %T.databaseBuilder<%T>(context, dbFile.absolutePath)",
            ClassName("androidx.room3", "Room"),
            databaseType,
          )
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }

  private fun writeJvmActual(
    packageName: String,
    fileName: String,
    databaseType: ClassName,
    dbFileName: String,
  ) {
    fileBuilder(packageName, fileName) {
      addImport("java.io", "File")
      addFunction(
        FunSpec.builder("getDatabaseBuilder")
          .addModifiers(KModifier.ACTUAL)
          .returns(
            ClassName("androidx.room3", "RoomDatabase", "Builder")
              .parameterizedBy(databaseType),
          )
          .addStatement(
            "val dbFile = File(System.getProperty(%S), %S)",
            "java.io.tmpdir",
            dbFileName,
          )
          .addStatement(
            "return %T.databaseBuilder<%T>(name = dbFile.absolutePath).setDriver(%T())",
            ClassName("androidx.room3", "Room"),
            databaseType,
            ClassName("androidx.sqlite.driver.bundled", "BundledSQLiteDriver"),
          )
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }

  private fun writeIosActual(
    packageName: String,
    fileName: String,
    databaseType: ClassName,
    dbFileName: String,
  ) {
    fileBuilder(packageName, fileName) {
      addImport("platform.Foundation", "NSHomeDirectory")
      addFunction(
        FunSpec.builder("getDatabaseBuilder")
          .addModifiers(KModifier.ACTUAL)
          .returns(
            ClassName("androidx.room3", "RoomDatabase", "Builder")
              .parameterizedBy(databaseType),
          )
          .addStatement(
            "val dbFilePath = NSHomeDirectory() + %S",
            "/$dbFileName",
          )
          .addStatement(
            "return %T.databaseBuilder<%T>(name = dbFilePath).setDriver(%T())",
            ClassName("androidx.room3", "Room"),
            databaseType,
            ClassName("androidx.sqlite.driver.bundled", "BundledSQLiteDriver"),
          )
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }

  private fun writeWebActual(
    packageName: String,
    fileName: String,
    databaseType: ClassName,
    dbFileName: String,
  ) {
    fileBuilder(packageName, fileName) {
      addFunction(
        FunSpec.builder("getDatabaseBuilder")
          .addModifiers(KModifier.ACTUAL)
          .returns(
            ClassName("androidx.room3", "RoomDatabase", "Builder")
              .parameterizedBy(databaseType),
          )
          .addStatement(
            "return %T.databaseBuilder<%T>(name = %S, factory = { %T.initialize() }).setDriver(%T())",
            ClassName("androidx.room3", "Room"),
            databaseType,
            dbFileName,
            ClassName(packageName, "${databaseType.simpleName}Constructor"),
            ClassName("dev.teogor.stitch.web", "createSQLiteWebDriver"),
          )
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }
}

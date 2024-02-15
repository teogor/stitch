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
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.teogor.stitch.codegen.commons.DAGGER_INSTALL_IN
import dev.teogor.stitch.codegen.commons.DAGGER_MODULE
import dev.teogor.stitch.codegen.commons.DAGGER_PROVIDES
import dev.teogor.stitch.codegen.commons.DAGGER_SINGLETON_COMPONENT
import dev.teogor.stitch.codegen.commons.JAVAX_INJECT_SINGLETON
import dev.teogor.stitch.codegen.commons.fileBuilder
import dev.teogor.stitch.codegen.commons.shortName
import dev.teogor.stitch.codegen.commons.writeWith
import dev.teogor.stitch.codegen.facades.CodeOutputStreamMaker
import dev.teogor.stitch.codegen.model.CodeGenConfig
import dev.teogor.stitch.codegen.model.DatabaseModel
import dev.teogor.stitch.codegen.model.RoomModel
import dev.teogor.stitch.codegen.servicelocator.OutputWriter

class StitchModuleOutputWriter(
  private val codeOutputStreamMaker: CodeOutputStreamMaker,
  codeGenConfig: CodeGenConfig,
) : OutputWriter(codeGenConfig) {

  fun write(
    databaseModels: Sequence<DatabaseModel>,
    roomModels: List<RoomModel>,
  ) {
    val packageName = roomModels.first().getPackageName()
    fileBuilder(
      packageName = "$packageName.di",
      fileName = "StitchModule",
    ) {
      addType(
        TypeSpec.objectBuilder("StitchModule")
          .addAnnotation(DAGGER_MODULE)
          .addAnnotation(
            AnnotationSpec.builder(DAGGER_INSTALL_IN)
              .addMember("%T::class", DAGGER_SINGLETON_COMPONENT)
              .build(),
          )
          .addDocumentation(
            """
            This object provides the Stitch module for dependency injection.

            It configures and provides necessary dependencies for Stitch-related components,
            including DAOs and repositories.
            """.trimIndent(),
          )
          .apply {
            addFunction(
              FunSpec.builder("provideAppDatabase")
                .addAnnotation(DAGGER_PROVIDES)
                .addParameter("app", ClassName("android.app", "Application"))
                .returns(databaseModels.first().type)
                .addDocumentation(
                  """
                  Provides an instance of the [${databaseModels.first().type.shortName}] for dependency injection.

                  @param app The application context for accessing the database.

                  @return The created [${databaseModels.first().type.shortName}] instance.
                  """.trimIndent(),
                )
                .addStatement(
                  "return %T.getInstance(context = app)",
                  databaseModels.first().type,
                )
                .build(),
            )
            roomModels.filter { it.hasDao }.forEach { roomModel ->
              val database = databaseModels.firstOrNull {
                it.entities.contains(roomModel.entity)
              } ?: databaseModels.first()
              val function = database.functions.firstOrNull { it.returnType == roomModel.dao }
              if (function != null) {
                addFunction(
                  FunSpec.builder("provide${roomModel.name}Dao")
                    .addDocumentation(
                      """
                    Provides the [${roomModel.name}Dao] instance.

                    @param db The [${database.type.shortName}] instance.

                    @see [${roomModel.name}Dao]
                    @see [${database.type.shortName}]
                      """.trimIndent(),
                    )
                    .addAnnotation(JAVAX_INJECT_SINGLETON)
                    .addAnnotation(DAGGER_PROVIDES)
                    .returns(
                      ClassName(
                        "${roomModel.packageName}.dao",
                        "${roomModel.name}Dao",
                      ),
                    )
                    .addParameter(
                      ParameterSpec.builder(
                        "db",
                        database.type,
                      ).build(),
                    )
                    .addStatement("return db.${function.name}()")
                    .build(),
                )
              }
              addFunction(
                FunSpec.builder("provide${roomModel.name}Repository")
                  .addDocumentation(
                    """
                    Provides the [${roomModel.name}Repository] using the provided DAO.

                    @param dao The [${roomModel.name}Dao] instance.

                    @see [${roomModel.name}Dao]
                    @see [${roomModel.name}Repository]
                    """.trimIndent(),
                  )
                  .addAnnotation(JAVAX_INJECT_SINGLETON)
                  .addAnnotation(DAGGER_PROVIDES)
                  .returns(
                    ClassName(
                      "${roomModel.getPackageName()}.data.repository",
                      "${roomModel.name}Repository",
                    ),
                  )
                  .addParameter(
                    ParameterSpec.builder(
                      "dao",
                      ClassName(
                        "${roomModel.packageName}.dao",
                        "${roomModel.name}Dao",
                      ),
                    ).build(),
                  )
                  .addStatement(
                    "return %T(dao)",
                    ClassName(
                      "${roomModel.getPackageName()}.data.repository.impl",
                      "${roomModel.name}RepositoryImpl",
                    ),
                  )
                  .build(),
              )
            }
          }
          .build(),
      )
    }.writeWith(codeOutputStreamMaker)
  }
}

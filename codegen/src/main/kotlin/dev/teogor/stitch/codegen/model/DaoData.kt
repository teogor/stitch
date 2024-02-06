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

package dev.teogor.stitch.codegen.model

import com.squareup.kotlinpoet.TypeName

data class FunctionData(val name: String)

data class DatabaseModel(
    val entities: List<TypeName>,
    val type: TypeName,
    val functions: List<FunctionKind>,
)

data class RoomModel(
    val name: String,
    val packageName: String,
    val entity: TypeName,
    val dao: TypeName,
    val fields: List<FieldKind>,
    val functions: List<FunctionKind>,
)

data class FieldKind(
    val name: String,
    val type: TypeName,
)

data class FunctionKind(
    val name: String,
    val isSuspend: Boolean,
    val returnType: TypeName,
    val parameters: List<ParameterKind>,
)

data class ParameterKind(
    val name: String,
    val type: TypeName,
)

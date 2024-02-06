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

package dev.teogor.stitch.codegen.commons

import com.squareup.kotlinpoet.ClassName

const val CORE_PACKAGE_NAME = "dev.teogor.stitch"
const val ROOM_PACKAGE_NAME = "androidx.room"

const val DESTINATION_ANNOTATION = "Destination"
const val DAO_ANNOTATION = "Dao"
const val ENTITY_ANNOTATION = "Entity"
const val ACTIVITY_DESTINATION_ANNOTATION = "ActivityDestination"
const val NAV_GRAPH_ANNOTATION = "NavGraph"
const val NAV_TYPE_SERIALIZER_ANNOTATION = "NavTypeSerializer"
const val DAO_ANNOTATION_QUALIFIED = "$ROOM_PACKAGE_NAME.$DAO_ANNOTATION"

val DAGGER_MODULE = ClassName("dagger", "Module")
val DAGGER_PROVIDES = ClassName("dagger", "Provides")
val DAGGER_INSTALL_IN = ClassName("dagger.hilt", "InstallIn")
val DAGGER_APPLICATION_CONTEXT = ClassName("dagger.hilt.android.qualifiers", "ApplicationContext")
val DAGGER_SINGLETON_COMPONENT = ClassName("dagger.hilt.components", "SingletonComponent")
val JAVAX_INJECT_SINGLETON = ClassName("javax.inject", "Singleton")
val JAVAX_INJECT = ClassName("javax.inject", "Inject")

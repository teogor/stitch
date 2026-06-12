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

import kotlin.reflect.KClass

/**
 * Annotation to mark a Room Entity to be mapped to a domain model.
 *
 * When applied to an Entity, Stitch will use the specified [target] class in the
 * generated Repository interface instead of the Entity itself.
 *
 * The generated Repository Implementation will expect extension functions:
 * - `Entity.toDomain(): Target`
 * - `Target.toEntity(): Entity`
 *
 * @property target The domain model class to map to.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MapTo(val target: KClass<*>)

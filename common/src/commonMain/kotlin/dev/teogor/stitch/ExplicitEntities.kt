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
 * Explicitly defines the entities this DAO interacts with for safer mapping and improved type
 * safety.
 *
 * This annotation is used to associate a DAO class with the specific entity classes it manages.
 * It serves several purposes:
 *
 * - **Enhanced type safety:** By explicitly listing the entities, you ensure the DAO methods
 * operate on the intended types, reducing potential type errors and improving code clarity.
 * - **Safer mapping:** Knowing the exact entities allows code generation tools to perform more
 * accurate mapping between DAOs and entities, mitigating potential mapping issues.
 * - **Clearer intent:** This annotation makes the relationship between the DAO and its entities
 * explicit, improving code readability and maintainability.
 *
 * @param entities [Array]<[KClass<*>>]>: The list of entity classes associated with the DAO.
 * Defaults to an empty array.
 * @param isExclusive [Boolean]: (Optional) Specifies whether only the listed entities are managed
 * by this DAO. Defaults to `false`.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class ExplicitEntities(
  val entities: Array<KClass<*>> = [],
  val isExclusive: Boolean = false,
)

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

/**
 * Annotation for providing custom names for generated repositories and their implementations.
 *
 * When applied to a Room DAO, Stitch will use the specified [repository] and [implementation]
 * names instead of the default generated ones.
 *
 * @property repository The custom name for the generated repository interface.
 * @property implementation The custom name for the generated repository implementation class.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class StitchName(
  val repository: String = "",
  val implementation: String = "",
)

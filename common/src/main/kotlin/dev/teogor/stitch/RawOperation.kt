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
 * An annotation for marking DAO methods that should be generated as raw
 * operations.
 *
 * This annotation instructs the Stitch plugin to generate separate operation
 * classes for annotated methods in your DAOs. These operation classes provide
 * a convenient way to invoke and manage the corresponding database queries
 * directly.
 *
 * @param generate [Boolean] (default: `true`) Whether to generate the operation
 * class for this method.
 *
 * @see Operation
 * @see OperationSignature
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class RawOperation(
  val generate: Boolean = true,
)

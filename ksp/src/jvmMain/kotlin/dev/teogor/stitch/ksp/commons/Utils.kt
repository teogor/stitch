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

package dev.teogor.stitch.ksp.commons

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * Checks if this class declaration has an annotation of the specified type `T`.
 *
 * @return `true` if the class has at least one annotation of type `T`, `false` otherwise.
 */
inline fun <reified T> KSClassDeclaration.firstAnnotation(): KSAnnotation? {
  return annotations.firstOrNull {
    it.annotationType.resolve().declaration.qualifiedName!!.asString() == T::class.qualifiedName
  }
}

/**
 * Finds the value of an argument with the specified name in this annotation.
 *
 * @param T The type of the argument value expected.
 * @param name The name of the argument to find.
 * @return The value of the argument as type `T`, or `null` if not found or is not of the
 * expected type.
 */
inline fun <reified T> KSAnnotation.findArgumentValue(name: String): T? {
  return arguments.find { it.name?.asString() == name }?.value as T?
}

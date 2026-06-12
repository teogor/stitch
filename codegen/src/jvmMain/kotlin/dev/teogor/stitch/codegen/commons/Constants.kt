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

val METRO_BINDING_CONTAINER = ClassName(
  packageName = "dev.zacsweers.metro",
  "BindingContainer",
)
val METRO_PROVIDES = ClassName(
  packageName = "dev.zacsweers.metro",
  "Provides",
)
val METRO_CONTRIBUTES_TO = ClassName(
  packageName = "dev.zacsweers.metro",
  "ContributesTo",
)
val STITCH_SCOPE = ClassName(
  packageName = "dev.teogor.stitch.di",
  "StitchScope",
)
val METRO_SINGLE_IN = ClassName(
  packageName = "dev.zacsweers.metro",
  "SingleIn",
)
val METRO_INJECT = ClassName(
  packageName = "dev.zacsweers.metro",
  "Inject",
)
val JAVAX_INJECT = ClassName(
  packageName = "javax.inject",
  "Inject",
)

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

val DAGGER_MODULE = ClassName(
  packageName = "dagger",
  "Module",
)
val DAGGER_PROVIDES = ClassName(
  packageName = "dagger",
  "Provides",
)
val DAGGER_INSTALL_IN = ClassName(
  packageName = "dagger.hilt",
  "InstallIn",
)
val DAGGER_APPLICATION_CONTEXT = ClassName(
  packageName = "dagger.hilt.android.qualifiers",
  "ApplicationContext",
)
val DAGGER_SINGLETON_COMPONENT = ClassName(
  packageName = "dagger.hilt.components",
  "SingletonComponent",
)
val JAVAX_INJECT_SINGLETON = ClassName(
  packageName = "javax.inject",
  "Singleton",
)
val JAVAX_INJECT = ClassName(
  packageName = "javax.inject",
  "Inject",
)

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

package dev.teogor.stitch.runtime

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@PublishedApi
internal actual object StitchPathResolver {
  @OptIn(ExperimentalForeignApi::class)
  actual fun resolve(name: String, strategy: DatabasePath): String {
    return when (strategy) {
      is DatabasePath.Internal -> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
          directory = NSDocumentDirectory,
          inDomain = NSUserDomainMask,
          appropriateForURL = null,
          create = false,
          error = null,
        )
        (documentDirectory?.path ?: "") + "/$name"
      }

      is DatabasePath.Temporary -> {
        val cacheDirectory = NSFileManager.defaultManager.URLForDirectory(
          directory = NSCachesDirectory,
          inDomain = NSUserDomainMask,
          appropriateForURL = null,
          create = false,
          error = null,
        )
        (cacheDirectory?.path ?: "") + "/$name"
      }

      is DatabasePath.Custom -> "${strategy.absoluteDirectoryPath}/$name"
      is DatabasePath.InMemory -> ""
    }
  }
}

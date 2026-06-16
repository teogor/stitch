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

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object StitchRuntime {
  private var applicationContext: Context? = null

  /**
   * Initializes Stitch framework configuration bounds for Android targets.
   * This is called automatically by [dev.teogor.stitch.runtime.startup.StitchInitializer].
   */
  @PublishedApi
  internal actual fun initialize(context: PlatformContext) {
    if (applicationContext == null) {
      applicationContext = context.context.applicationContext
    }
  }

  @PublishedApi
  internal actual fun getContext(): PlatformContext {
    val context = applicationContext ?: throw IllegalStateException(
      "Stitch Framework has not been initialized! Please ensure App Startup is working.",
    )
    return PlatformContext(context)
  }

  actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO
}

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

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Global entry point for the Stitch framework lifecycle management.
 */
expect object StitchRuntime {
  /**
   * Returns the platform-specific IO dispatcher.
   */
  val ioDispatcher: CoroutineDispatcher

  /**
   * Internal function to initialize the framework.
   * On Android, this is handled automatically by App Startup.
   */
  @PublishedApi
  internal fun initialize(context: PlatformContext)

  /**
   * Returns the current platform context.
   */
  @PublishedApi
  internal fun getContext(): PlatformContext
}

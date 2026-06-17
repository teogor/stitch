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

package dev.teogor.stitch.runtime.startup

import android.content.Context
import androidx.startup.Initializer
import dev.teogor.stitch.runtime.PlatformContext
import dev.teogor.stitch.runtime.StitchRuntime

/**
 * Automates Stitch framework initialization on Android using App Startup.
 */
class StitchInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        StitchRuntime.initialize(PlatformContext(context))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

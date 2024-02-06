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

package dev.teogor.stitch.data.converters

import androidx.room.TypeConverter
import java.time.Duration

/**
 * Converts Duration
 */
class DurationConverter {
    /**
     * Converts seconds to Duration
     * @param value Duration in seconds
     * @return duration of seconds
     */
    @TypeConverter
    fun toDuration(value: Long): Duration {
        return Duration.ofSeconds(value)
    }

    /**
     * Converts Duration to seconds
     * @param duration Duration
     * @return duration represented in seconds
     */
    @TypeConverter
    fun fromDuration(duration: Duration): Long {
        return duration.seconds
    }
}
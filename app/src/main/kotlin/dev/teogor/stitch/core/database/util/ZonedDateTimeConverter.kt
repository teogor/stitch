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

package dev.teogor.stitch.core.database.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Converts date represented by Instant to Long seconds and reverse
 */
class ZonedDateTimeConverter {
    /**
     * Converts date in seconds to ZoneDateTime with system default time zone
     * @param value date in seconds
     * @return ZonedDateTime of seconds
     */
    @TypeConverter
    fun toZonedDateTime(value: Long?): ZonedDateTime? {
        return if (value != null) {
            ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(value),
                ZoneId.systemDefault(),
            )
        } else {
            null
        }
    }

    /**
     * Converts ZonedDateTime to seconds
     * @param zonedDateTime date
     */
    @TypeConverter
    fun fromZonedDateTime(zonedDateTime: ZonedDateTime?): Long? {
        return zonedDateTime?.toInstant()?.epochSecond
    }
}

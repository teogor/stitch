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

package com.zeoowl.beatifyd.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  indices = [
    Index(
      value = [
        "playlist_creator_id",
        "id",
      ],
      unique = true,
    ),
  ],
)
class Song(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "song_key")
  val songPrimaryKey: Long = 0L,

  @ColumnInfo(name = "playlist_creator_id")
  val playlistCreatorId: Long,

  val id: Long,

  val title: String,

  @ColumnInfo(name = "track_number")
  val trackNumber: Int,

  val year: Int,

  val duration: Long,

  val data: String,

  @ColumnInfo(name = "date_modified")
  val dateModified: Long,

  @ColumnInfo(name = "album_id")
  val albumId: Long,

  @ColumnInfo(name = "album_name")
  val albumName: String,

  @ColumnInfo(name = "artist_id")
  val artistId: Long,

  @ColumnInfo(name = "artist_name")
  val artistName: String,

  val composer: String?,

  @ColumnInfo(name = "album_artist")
  val albumArtist: String?,
)

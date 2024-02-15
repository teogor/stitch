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

package com.zeoowl.beatifyd.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.zeoowl.beatifyd.core.data.model.Playlist
import com.zeoowl.beatifyd.core.data.model.PlaylistWithSongs
import com.zeoowl.beatifyd.core.data.model.Song
import dev.teogor.stitch.ExplicitEntities
import kotlinx.coroutines.flow.Flow

@Dao
@ExplicitEntities(
  entities = [
    Song::class,
  ],
)
interface PlaylistDao {
  @Insert
  suspend fun createPlaylist(playlist: Playlist): Long

  @Query("UPDATE Playlist SET playlist_name = :name WHERE playlist_id = :playlistId")
  suspend fun renamePlaylist(playlistId: Long, name: String)

  @Query("SELECT * FROM Playlist WHERE playlist_name = :name")
  fun playlist(name: String): List<Playlist>

  @Query("SELECT * FROM Playlist")
  suspend fun playlists(): List<Playlist>

  @Query("DELETE FROM Song WHERE playlist_creator_id = :playlistId")
  suspend fun deletePlaylistSongs(playlistId: Long)

  @Query("DELETE FROM Song WHERE playlist_creator_id = :playlistId AND id = :songId")
  suspend fun deleteSongFromPlaylist(playlistId: Long, songId: Long)

  @Transaction
  @Query("SELECT * FROM Playlist")
  suspend fun playlistsWithSongs(): List<PlaylistWithSongs>

  @Transaction
  @Query("SELECT * FROM Playlist WHERE playlist_id= :playlistId")
  fun getPlaylist(playlistId: Long): Flow<PlaylistWithSongs>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSongsToPlaylist(songEntities: List<Song>)

  @Query("SELECT * FROM Song WHERE playlist_creator_id = :playlistId AND id = :songId")
  suspend fun isSongExistsInPlaylist(playlistId: Long, songId: Long): List<Song>

  @Query("SELECT * FROM Song WHERE playlist_creator_id = :playlistId ORDER BY song_key asc")
  fun songsFromPlaylist(playlistId: Long): Flow<List<Song>>

  @Delete
  suspend fun deletePlaylist(playlist: Playlist)

  @Delete
  suspend fun deletePlaylists(playlistEntities: List<Playlist>)

  @Delete
  suspend fun deletePlaylistSongs(songs: List<Song>)

  @RewriteQueriesToDropUnusedColumns
  @Query(
    "SELECT * FROM Song ,(SELECT playlist_id FROM Playlist WHERE playlist_name= :playlistName LIMIT 1) AS playlist WHERE playlist_creator_id= playlist.playlist_id",
  )
  fun favoritesSongsLiveData(playlistName: String): Flow<List<Song>>

  @Query("SELECT * FROM Song WHERE playlist_creator_id= :playlistId")
  fun favoritesSongs(playlistId: Long): List<Song>

  @Query("SELECT EXISTS(SELECT * FROM Playlist WHERE playlist_id = :playlistId)")
  fun checkPlaylistExists(playlistId: Long): Flow<Boolean>
}

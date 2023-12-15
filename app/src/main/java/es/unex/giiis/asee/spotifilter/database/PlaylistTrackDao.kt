package es.unex.giiis.asee.spotifilter.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack

@Dao
interface PlaylistTrackDao {

    @Delete
    suspend fun delete(playlistTrack: PlaylistTrack)

    @Query("SELECT * FROM playlisttrack WHERE playlistid = :playlistId AND trackid = :trackId")
    suspend fun findByPlaylistIdAndTrackId(playlistId: Long, trackId: String): PlaylistTrack?

    @Query("SELECT * FROM playlisttrack WHERE trackid = :trackId")
    suspend fun findByTrackId(trackId: String): List<PlaylistTrack>

    @Insert
    suspend fun insert(playlistTrack: PlaylistTrack): Long

}
package es.unex.giiis.asee.spotifilter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack

@Dao
interface PlaylistTrackDao {

    @Query("SELECT * FROM playlisttrack WHERE playlistid = :playlistId AND trackid = :trackId")
    suspend fun findByPlaylistIdAndTrackId(playlistId: Long, trackId: String): PlaylistTrack?

    @Insert
    suspend fun insert(playlistTrack: PlaylistTrack): Long

}
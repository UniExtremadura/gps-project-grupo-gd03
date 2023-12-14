package es.unex.giiis.asee.spotifilter.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.Track

@Dao
interface TrackDao {

    @Delete
    suspend fun delete(track: Track)

    @Query("SELECT * FROM track WHERE id = :id")
    suspend fun findById(id: String): Track?

    @Query("SELECT * FROM track JOIN playlisttrack ON id = trackid " +
            "WHERE playlistid = :playlistId AND artists LIKE '%' || :artists || '%' " +
            "ORDER BY releasedate DESC")
    suspend fun findByPlaylistIdOrderByReleaseDate(playlistId: Long?, artists: String): List<Track>

    @Insert
    suspend fun insert(track: Track): Long

}
package es.unex.giiis.asee.spotifilter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.Playlist

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist WHERE userid = :userId ORDER BY name")
    suspend fun findByUserId(userId: Long?): List<Playlist>

    @Query("SELECT * FROM playlist WHERE name = :name AND userid = :userId")
    suspend fun findByNameAndUserId(name: String, userId: Long?): Playlist?

    @Insert
    suspend fun insert(playlist: Playlist): Long

}
package es.unex.giiis.asee.spotifilter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM track WHERE id = :id")
    suspend fun findById(id: String): Track?

    @Insert
    suspend fun insert(track: Track): Long

}
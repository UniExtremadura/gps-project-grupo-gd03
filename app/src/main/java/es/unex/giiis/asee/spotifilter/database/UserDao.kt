package es.unex.giiis.asee.spotifilter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.spotifilter.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun findByName(name: String): User?

    @Insert
    suspend fun insert(user: User): Long

}
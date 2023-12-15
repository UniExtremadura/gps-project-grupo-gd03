package es.unex.giiis.asee.spotifilter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.data.model.User

@Database(entities = [User::class, Playlist::class, Track::class, PlaylistTrack::class], version = 1)
abstract class SpotiFilterDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackDao(): TrackDao
    abstract fun playlistTrackDao(): PlaylistTrackDao

    companion object {

        private var INSTANCE: SpotiFilterDatabase? = null

        fun destroyInstance() {
            INSTANCE = null
        }

        fun getInstance(context: Context): SpotiFilterDatabase? {
            if (INSTANCE == null) {
                synchronized(SpotiFilterDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, SpotiFilterDatabase::class.java,
                        "spotifilter.db").build()
                }
            }
            return INSTANCE
        }

    }

}
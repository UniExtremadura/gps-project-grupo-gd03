package es.unex.giiis.asee.spotifilter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.asee.spotifilter.data.model.User

@Database(entities = [User::class], version = 2)
abstract class SpotiFilterDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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
package es.unex.giiis.asee.spotifilter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val name: String,
    val userId: Long
) : Serializable
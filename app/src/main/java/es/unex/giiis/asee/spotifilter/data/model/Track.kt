package es.unex.giiis.asee.spotifilter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Track (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var album: String,
    val artists: String,
    val minutes: Int,
    val seconds: Int,
    var image: String,
    val name: String,
    var popularity: Int,
    var releaseDate: String
) : Serializable
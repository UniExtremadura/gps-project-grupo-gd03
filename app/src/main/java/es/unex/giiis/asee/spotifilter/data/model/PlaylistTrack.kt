package es.unex.giiis.asee.spotifilter.data.model

import androidx.room.Entity
import java.io.Serializable

@Entity(primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrack(
    val playlistId: Long,
    val trackId: String
) : Serializable
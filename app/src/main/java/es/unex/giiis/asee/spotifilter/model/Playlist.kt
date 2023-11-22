package es.unex.giiis.asee.spotifilter.model

import java.io.Serializable

data class Playlist (
    val name: String,
    val tracks: List<Track> = emptyList()
) : Serializable
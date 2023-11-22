package es.unex.giiis.asee.spotifilter.model

import java.io.Serializable

data class Album (
    val image: String,
    val name: String,
    val artists: List<String> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val releaseDate: String
) : Serializable
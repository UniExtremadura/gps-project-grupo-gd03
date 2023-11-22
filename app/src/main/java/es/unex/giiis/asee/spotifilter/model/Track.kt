package es.unex.giiis.asee.spotifilter.model

import java.io.Serializable

data class Track (
    val name: String,
    val artists: List<String> = emptyList(),
    val album: Album,
    val durationMs: Int,
    val popularity: Int
) : Serializable
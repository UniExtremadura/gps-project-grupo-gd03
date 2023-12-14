package es.unex.giiis.asee.spotifilter.data.model

import java.io.Serializable

data class Album (
    val id: String,
    val artists: String,
    val image: String,
    val name: String,
    val releaseDate: String
) : Serializable
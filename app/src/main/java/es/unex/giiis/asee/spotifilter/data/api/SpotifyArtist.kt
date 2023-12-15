package es.unex.giiis.asee.spotifilter.data.api

import com.google.gson.annotations.SerializedName

data class SpotifyArtist (
    @SerializedName("name") var name: String? = null
)
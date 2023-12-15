package es.unex.giiis.asee.spotifilter.data.api.tracks

import com.google.gson.annotations.SerializedName

data class SpotifyTracks(
    @SerializedName("items") var items: List<SpotifyTrack>? = listOf()
)
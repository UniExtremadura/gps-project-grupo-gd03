package es.unex.giiis.asee.spotifilter.data.api.tracks

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistTracks(
    @SerializedName("items") var items: List<SpotifyPlaylistTrack>? = listOf()
)
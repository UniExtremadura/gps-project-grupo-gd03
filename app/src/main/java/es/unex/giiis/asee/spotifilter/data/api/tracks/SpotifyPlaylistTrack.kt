package es.unex.giiis.asee.spotifilter.data.api.tracks

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistTrack(
    @SerializedName("track") var track: SpotifyTrack? = null
)
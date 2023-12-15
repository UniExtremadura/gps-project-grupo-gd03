package es.unex.giiis.asee.spotifilter.data.api.tracks

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylist(
    @SerializedName("tracks") var tracks: SpotifyPlaylistTracks? = null
)
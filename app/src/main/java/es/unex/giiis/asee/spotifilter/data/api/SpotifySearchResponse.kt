package es.unex.giiis.asee.spotifilter.data.api

import com.google.gson.annotations.SerializedName
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTracks

data class SpotifySearchResponse(
    @SerializedName("tracks") var tracks: SpotifyTracks? = null
)
package es.unex.giiis.asee.spotifilter.data.api

import com.google.gson.annotations.SerializedName
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbums
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTracks

data class SpotifySearchResponse(
    @SerializedName("albums") var albums: SpotifyAlbums? = null,
    @SerializedName("tracks") var tracks: SpotifyTracks? = null
)
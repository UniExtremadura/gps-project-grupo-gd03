package es.unex.giiis.asee.spotifilter.data.api.tracks

import com.google.gson.annotations.SerializedName
import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum

data class SpotifyTrack (
    @SerializedName("album") var album: SpotifyAlbum? = null,
    @SerializedName("artists") var artists: List<SpotifyArtist>? = listOf(),
    @SerializedName("duration_ms") var durationMs: Int? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("popularity") var popularity: Int? = null
)
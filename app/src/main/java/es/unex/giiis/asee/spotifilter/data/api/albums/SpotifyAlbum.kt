package es.unex.giiis.asee.spotifilter.data.api.albums

import com.google.gson.annotations.SerializedName
import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist

data class SpotifyAlbum (
    @SerializedName("artists") var artists: List<SpotifyArtist>? = listOf(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("images") var images: List<SpotifyImage>? = listOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null
)
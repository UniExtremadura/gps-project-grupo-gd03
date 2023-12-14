package es.unex.giiis.asee.spotifilter.data.api.albums

import com.google.gson.annotations.SerializedName

data class SpotifyAlbums(
    @SerializedName("items") var items: List<SpotifyAlbum>? = listOf()
)
package es.unex.giiis.asee.spotifilter.data.api.albums

import com.google.gson.annotations.SerializedName

data class SpotifyPagedSet(
    @SerializedName("albums") var albums: SpotifyAlbums? = null
)
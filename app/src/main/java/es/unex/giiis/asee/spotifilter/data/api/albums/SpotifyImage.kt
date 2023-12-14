package es.unex.giiis.asee.spotifilter.data.api.albums

import com.google.gson.annotations.SerializedName

data class SpotifyImage(
    @SerializedName("url") var url: String? = null
)
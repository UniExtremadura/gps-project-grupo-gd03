package es.unex.giiis.asee.spotifilter.data.api

import com.google.gson.annotations.SerializedName

data class SpotifyAuthorization(
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("token_type") var tokenType: String? = null
)
package es.unex.giiis.asee.spotifilter.api

import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyPagedSet
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylist
import es.unex.giiis.asee.spotifilter.data.api.SpotifySearchResponse
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTracks
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private val service: SpotifyAPI by lazy {
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
    val retrofit = Retrofit.Builder().baseUrl("https://api.spotify.com/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
    retrofit.create(SpotifyAPI::class.java)
}

fun getSpotifyAPIService() = service

interface SpotifyAPI {

    @GET("v1/browse/new-releases")
    suspend fun getSpotifyNewReleases(
        @Header("Authorization") authorization: String
    ): SpotifyPagedSet

    @GET("v1/playlists/{id}")
    suspend fun getSpotifyPlaylist(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): SpotifyPlaylist

    @GET("v1/search")
    suspend fun getSpotifySearchResponse(
        @Header("Authorization") authorization: String,
        @Query("q") q: String,
        @Query("type") type: String
    ): SpotifySearchResponse

    @GET("v1/tracks/{id}")
    suspend fun getSpotifyTrack(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): SpotifyTrack

    @GET("v1/albums/{id}/tracks")
    suspend fun getSpotifyAlbumTracks(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): SpotifyTracks

}

class SpotifyAPIError(message: String, cause: Throwable?) : Throwable(message, cause)
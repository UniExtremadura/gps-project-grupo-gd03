package es.unex.giiis.asee.spotifilter.api

import es.unex.giiis.asee.spotifilter.data.api.SpotifyAuthorization
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private val service: SpotifyAccounts by lazy {
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
    val retrofit = Retrofit.Builder().baseUrl("https://accounts.spotify.com/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
    retrofit.create(SpotifyAccounts::class.java)
}

fun getSpotifyAccountsService() = service

interface SpotifyAccounts {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getSpotifyAuthorization(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String = "9f63e309ccc94899bcdcacc1a180c22e",
        @Field("client_secret") clientSecret: String = "9b08473e7d1e42af994fe584cb3418b9"
    ): SpotifyAuthorization

}

class SpotifyAccountsError(message: String, cause: Throwable?) : Throwable(message, cause)
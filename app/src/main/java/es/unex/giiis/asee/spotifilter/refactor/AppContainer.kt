package es.unex.giiis.asee.spotifilter.refactor

import android.content.Context
import es.unex.giiis.asee.spotifilter.api.getSpotifyAPIService
import es.unex.giiis.asee.spotifilter.api.getSpotifyAccountsService
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase

class AppContainer(context: Context?) {

    private val database = SpotiFilterDatabase.getInstance(context!!)

    val repository = Repository(database!!.playlistDao(), database!!.playlistTrackDao(),
        database!!.trackDao(), database!!.userDao(), getSpotifyAccountsService(),
        getSpotifyAPIService())

}
package es.unex.giiis.asee.spotifilter.data

import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.api.getSpotifyAPIService
import es.unex.giiis.asee.spotifilter.api.getSpotifyAccountsService
import es.unex.giiis.asee.spotifilter.data.api.SpotifyAuthorization
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylist
import es.unex.giiis.asee.spotifilter.data.api.SpotifySearchResponse
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import es.unex.giiis.asee.spotifilter.data.model.Track

suspend fun SpotifyTrack.toTrack(): Track {
    val track = Track(
        album = album?.name ?: "",
        artists = artists?.joinToString(separator = ", ") { it.name!! } ?: "",
        id = id ?: "",
        image = album?.images?.get(0)?.url ?: "",
        minutes = durationMs?.div(1000)?.floorDiv(60) ?: 0,
        name = name ?: "",
        popularity = popularity ?: 0,
        releaseDate = album?.releaseDate ?: "",
        seconds = durationMs?.div(1000)?.mod(60) ?: 0
    )
    if (track.popularity == 0) {
        try {
            val authorization = getSpotifyAccountsService().getSpotifyAuthorization()
                .getAuthorization()
            try {
                track.popularity = getSpotifyAPIService().getSpotifyTrack(authorization, id!!)
                    .popularity!!
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
    }
    return track
}

fun SpotifyAuthorization.getAuthorization(): String {
    return "$tokenType $accessToken"
}

suspend fun SpotifyPlaylist.getTracks(): List<Track> {
    val trackList = mutableListOf<Track>()
    for (item in tracks?.items!!) {
        trackList.add(item.track?.toTrack()!!)
    }
    return trackList
}

suspend fun SpotifySearchResponse.getTracks(): List<Track> {
    val trackList = mutableListOf<Track>()
    for (track in tracks?.items!!) {
        trackList.add(track.toTrack())
    }
    return trackList
}
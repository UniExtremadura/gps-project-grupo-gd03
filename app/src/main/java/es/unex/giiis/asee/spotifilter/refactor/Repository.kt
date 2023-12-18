package es.unex.giiis.asee.spotifilter.refactor

import android.content.Context
import android.widget.Toast
import es.unex.giiis.asee.spotifilter.api.SpotifyAPI
import es.unex.giiis.asee.spotifilter.api.SpotifyAPIError
import es.unex.giiis.asee.spotifilter.api.SpotifyAccounts
import es.unex.giiis.asee.spotifilter.api.SpotifyAccountsError
import es.unex.giiis.asee.spotifilter.data.getAlbums
import es.unex.giiis.asee.spotifilter.data.getAuthorization
import es.unex.giiis.asee.spotifilter.data.getTracks
import es.unex.giiis.asee.spotifilter.data.model.Album
import es.unex.giiis.asee.spotifilter.data.model.Playlist
import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import es.unex.giiis.asee.spotifilter.data.model.Track
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.database.dao.PlaylistDao
import es.unex.giiis.asee.spotifilter.database.dao.PlaylistTrackDao
import es.unex.giiis.asee.spotifilter.database.dao.TrackDao
import es.unex.giiis.asee.spotifilter.database.dao.UserDao

class Repository(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao,
    private val trackDao: TrackDao,
    private val userDao: UserDao,
    private val spotifyAccounts: SpotifyAccounts,
    private val spotifyAPI: SpotifyAPI
) {

    suspend fun findUserByName(name: String): User? {
        return userDao.findByName(name)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun fetchTopGlobal(): List<Track> {
        val topGlobal: List<Track>
        try {
            val authorization = spotifyAccounts.getSpotifyAuthorization().getAuthorization()
            try {
                topGlobal = spotifyAPI.getSpotifyPlaylist(authorization,
                    "37i9dQZEVXbMDoHDwVN2tF").getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return topGlobal
    }

    suspend fun fetchSearchedTracks(query: String): List<Track> {
        val searchedTracks: List<Track>
        try {
            val authorization = spotifyAccounts.getSpotifyAuthorization().getAuthorization()
            try {
                searchedTracks = spotifyAPI
                    .getSpotifySearchResponse(authorization, query, "track").getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return searchedTracks
    }

    suspend fun fetchNewReleases(): List<Album> {
        val newReleases: List<Album>
        try {
            val authorization = spotifyAccounts.getSpotifyAuthorization().getAuthorization()
            try {
                newReleases = spotifyAPI.getSpotifyNewReleases(authorization).getAlbums()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return newReleases
    }

    suspend fun fetchSearchedAlbums(query: String): List<Album> {
        val searchedAlbums: List<Album>
        try {
            val authorization = spotifyAccounts.getSpotifyAuthorization().getAuthorization()
            try {
                searchedAlbums = spotifyAPI
                    .getSpotifySearchResponse(authorization, query, "album").getAlbums()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return searchedAlbums
    }

    suspend fun fetchAlbumTracks(album: Album): List<Track> {
        val albumTracks: List<Track>
        try {
            val authorization = spotifyAccounts.getSpotifyAuthorization()
                .getAuthorization()
            try {
                albumTracks = spotifyAPI
                    .getSpotifyAlbumTracks(authorization, album.id).getTracks()
            } catch (cause: Throwable) {
                throw SpotifyAPIError("Unable to fetch data from API", cause)
            }
        } catch (cause: Throwable) {
            throw SpotifyAccountsError("Unable to get authorization", cause)
        }
        return albumTracks
    }

    suspend fun findPlaylistsByUserId(userId: Long?): List<Playlist> {
        return playlistDao.findByUserId(userId)
    }

    suspend fun findPlaylistByNameAndUserId(name: String, userId: Long?): Playlist? {
        return playlistDao.findByNameAndUserId(name, userId)
    }

    suspend fun insertPlaylist(playlist: Playlist): Long {
        return playlistDao.insert(playlist)
    }

    suspend fun addTrackToPlaylist(context: Context, playlist: Playlist, track: Track) {
        if (playlistTrackDao.findByPlaylistIdAndTrackId(playlist.id!!, track.id) != null
        ) {
            Toast.makeText(context, "'${playlist.name}' already contains '${track.name}'",
                Toast.LENGTH_SHORT).show()
        } else {
            val playlistTrack = PlaylistTrack(playlist.id!!, track.id)
            playlistTrackDao.insert(playlistTrack)
            if (trackDao.findById(track.id) == null) {
                trackDao.insert(track)
            }
            Toast.makeText(context, "'${track.name}' added to '${playlist.name}'",
                Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun insertPlaylistTrack(playlistTrack: PlaylistTrack): Long {
        return playlistTrackDao.insert(playlistTrack)
    }

    suspend fun findTrackById(id: String): Track? {
        return trackDao.findById(id)
    }

    suspend fun insertTrack(track: Track): Long {
        return trackDao.insert(track)
    }

    suspend fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        val playlistTrack = PlaylistTrack(playlist.id!!, track.id)
        playlistTrackDao.delete(playlistTrack)
        if (playlistTrackDao.findByTrackId(track.id).isEmpty()) {
            trackDao.delete(track)
        }
    }

    suspend fun findTracksByPlaylistIdOrderByReleaseDate(playlistId: Long?, artists: String)
    : List<Track> {
        return trackDao.findByPlaylistIdOrderByReleaseDate(playlistId, artists)
    }

    suspend fun findTracksByPlaylistIdOrderByPopularity(playlistId: Long?, artists: String)
    : List<Track> {
        return trackDao.findByPlaylistIdOrderByPopularity(playlistId, artists)
    }

}
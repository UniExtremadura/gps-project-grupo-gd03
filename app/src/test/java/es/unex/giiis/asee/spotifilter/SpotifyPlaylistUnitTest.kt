package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylist
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylistTrack
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylistTracks
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyPlaylistUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyPlaylist: SpotifyPlaylist) {
        val field = spotifyPlaylist.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyPlaylist), value)
    }

    @Test
    fun checkSpotifyPlaylistFields() {
        val tracksValue = SpotifyPlaylistTracks(listOf(SpotifyPlaylistTrack(SpotifyTrack(
            SpotifyAlbum(
                listOf(SpotifyArtist("Fred again.."), SpotifyArtist("Baby Keem")),
                "1MVqeIAwhD4T44AKVkIfic",
                listOf(
                    SpotifyImage("https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480")
                ),
                "leavemealone",
                "2023-12-08"
            ),
            listOf(SpotifyArtist("Fred again.."), SpotifyArtist("Baby Keem")),
            222000,
            "1MVqeIAwhD4T44AKVkIfic",
            "leavemealone",
            79
        ))))
        val spotifyPlaylist = SpotifyPlaylist(tracksValue)
        assertFieldEquals("tracks", tracksValue, spotifyPlaylist)
    }

}
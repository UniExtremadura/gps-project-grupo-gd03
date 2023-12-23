package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylistTrack
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyPlaylistTracks
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyPlaylistTracksUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any,
        spotifyPlaylistTracks: SpotifyPlaylistTracks
    ) {
        val field = spotifyPlaylistTracks.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyPlaylistTracks),
            value)
    }

    @Test
    fun checkSpotifyPlaylistTracksFields() {
        val itemsValue = listOf(SpotifyPlaylistTrack(SpotifyTrack(
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
        )))
        val spotifyPlaylistTracks = SpotifyPlaylistTracks(itemsValue)
        assertFieldEquals("items", itemsValue, spotifyPlaylistTracks)
    }

}
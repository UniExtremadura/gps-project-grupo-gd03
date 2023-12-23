package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.SpotifySearchResponse
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
<<<<<<< HEAD
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbums
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
=======
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTracks
>>>>>>> origin/develop
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifySearchResponseUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any,
        spotifySearchResponse: SpotifySearchResponse
    ) {
        val field = spotifySearchResponse.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifySearchResponse),
            value)
    }

    @Test
    fun checkSpotifySearchResponseFields() {
<<<<<<< HEAD
        val albumsValue = SpotifyAlbums(listOf(
            SpotifyAlbum(
                listOf(SpotifyArtist("Fred again.."), SpotifyArtist("Baby Keem")),
                "1MVqeIAwhD4T44AKVkIfic",
                listOf(SpotifyImage(
                    "https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480"
                )),
                "leavemealone",
                "2023-12-08"
            )
        ))
=======
        val tracksValue = SpotifyTracks(listOf(
            SpotifyTrack(
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
        val spotifySearchResponse = SpotifySearchResponse(albumsValue, tracksValue)
        assertFieldEquals("tracks", tracksValue, spotifySearchResponse)
        assertFieldEquals("albums", albumsValue, spotifySearchResponse)
>>>>>>> origin/develop
    }

}
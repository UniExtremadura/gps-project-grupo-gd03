package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import es.unex.giiis.asee.spotifilter.data.api.tracks.SpotifyTrack
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyTrackUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyTrack: SpotifyTrack) {
        val field = spotifyTrack.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyTrack), value)
    }

    @Test
    fun checkSpotifyTrackFields() {
        val albumValue = SpotifyAlbum(
            listOf(SpotifyArtist("Fred again.."), SpotifyArtist("Baby Keem")),
            "1MVqeIAwhD4T44AKVkIfic",
            listOf(
                SpotifyImage("https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480")
            ),
            "leavemealone",
            "2023-12-08"
        )
        val artistsValue = listOf(SpotifyArtist("Fred again.."),
            SpotifyArtist("Baby Keem"))
        val durationMsValue = 222000
        val idValue = "1MVqeIAwhD4T44AKVkIfic"
        val nameValue = "leavemealone"
        val popularityValue = 79
        val spotifyTrack = SpotifyTrack(albumValue, artistsValue, durationMsValue, idValue,
            nameValue, popularityValue)
        assertFieldEquals("album", albumValue, spotifyTrack)
        assertFieldEquals("artists", artistsValue, spotifyTrack)
        assertFieldEquals("durationMs", durationMsValue, spotifyTrack)
        assertFieldEquals("id", idValue, spotifyTrack)
        assertFieldEquals("name", nameValue, spotifyTrack)
        assertFieldEquals("popularity", popularityValue, spotifyTrack)
    }

}
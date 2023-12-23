package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyArtistUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyArtist: SpotifyArtist) {
        val field = spotifyArtist.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyArtist), value)
    }

    @Test
    fun checkSpotifyArtistFields() {
        val nameValue = "Fred again.."
        val spotifyArtist = SpotifyArtist(nameValue)
        assertFieldEquals("name", nameValue, spotifyArtist)
    }

}
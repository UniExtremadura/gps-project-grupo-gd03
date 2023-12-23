package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyImageUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyImage: SpotifyImage) {
        val field = spotifyImage.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyImage), value)
    }

    @Test
    fun checkSpotifyImageFields() {
        val urlValue = "https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480"
        val spotifyImage = SpotifyImage(urlValue)
        assertFieldEquals("url", urlValue, spotifyImage)
    }

}
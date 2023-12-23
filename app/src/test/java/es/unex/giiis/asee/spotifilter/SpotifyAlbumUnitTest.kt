package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyAlbumUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyAlbum: SpotifyAlbum) {
        val field = spotifyAlbum.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyAlbum), value)
    }

    @Test
    fun checkSpotifyAlbumFields() {
        val artistsValue = listOf(SpotifyArtist("Fred again.."),
            SpotifyArtist("Baby Keem"))
        val idValue = "1MVqeIAwhD4T44AKVkIfic"
        val imageValue = listOf(
            SpotifyImage("https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480"))
        val nameValue = "leavemealone"
        val releaseDateValue = "2023-12-08"
        val spotifyAlbum = SpotifyAlbum(artistsValue, idValue, imageValue, nameValue, releaseDateValue)
        assertFieldEquals("artists", artistsValue, spotifyAlbum)
        assertFieldEquals("id", idValue, spotifyAlbum)
        assertFieldEquals("images", imageValue, spotifyAlbum)
        assertFieldEquals("name", nameValue, spotifyAlbum)
        assertFieldEquals("releaseDate", releaseDateValue, spotifyAlbum)
    }

}
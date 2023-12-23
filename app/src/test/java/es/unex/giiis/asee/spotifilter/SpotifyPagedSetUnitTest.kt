package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyArtist
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbum
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyAlbums
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyImage
import es.unex.giiis.asee.spotifilter.data.api.albums.SpotifyPagedSet
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyPagedSetUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, spotifyPagedSet: SpotifyPagedSet) {
        val field = spotifyPagedSet.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyPagedSet), value)
    }

    @Test
    fun checkSpotifyPagedSetFields() {
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
        val spotifyPagedSet = SpotifyPagedSet(albumsValue)
        assertFieldEquals("albums", albumsValue, spotifyPagedSet)
    }

}
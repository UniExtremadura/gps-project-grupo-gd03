package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.model.Album
import org.junit.Test
import org.junit.Assert.assertEquals

class AlbumUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, album: Album) {
        val field = album.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(album), value)
    }

    @Test
    fun checkAlbumFields() {
        val idValue = "1MVqeIAwhD4T44AKVkIfic"
        val artistsValue = "Fred again.., Baby Keem"
        val imageValue = "https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480"
        val nameValue = "leavemealone"
        val releaseDateValue = "2023-12-08"
        val album = Album(idValue, artistsValue, imageValue, nameValue, releaseDateValue)
        assertFieldEquals("id", idValue, album)
        assertFieldEquals("artists", artistsValue, album)
        assertFieldEquals("image", imageValue, album)
        assertFieldEquals("name", nameValue, album)
        assertFieldEquals("releaseDate", releaseDateValue, album)
    }

}
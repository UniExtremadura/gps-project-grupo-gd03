package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.model.Playlist
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, playlist: Playlist) {
        val field = playlist.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(playlist), value)
    }

    @Test
    fun checkPlaylistFields() {
        val idValue = (1).toLong()
        val nameValue = "Electro"
        val userIdValue: Long = (1).toLong()
        val playlist = Playlist(idValue, nameValue, userIdValue)
        assertFieldEquals("id", idValue, playlist)
        assertFieldEquals("name", nameValue, playlist)
        assertFieldEquals("userId", userIdValue, playlist)
    }

}
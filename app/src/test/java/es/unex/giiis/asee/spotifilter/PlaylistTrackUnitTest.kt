package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.model.PlaylistTrack
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistTrackUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, playlistTrack: PlaylistTrack) {
        val field = playlistTrack.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(playlistTrack), value)
    }

    @Test
    fun checkPlaylistFields() {
        val playlistIdValue = (1).toLong()
        val trackIdValue = "1"
        val playlistTrack = PlaylistTrack(playlistIdValue, trackIdValue)
        assertFieldEquals("playlistId", playlistIdValue, playlistTrack)
        assertFieldEquals("trackId", trackIdValue, playlistTrack)
    }

}
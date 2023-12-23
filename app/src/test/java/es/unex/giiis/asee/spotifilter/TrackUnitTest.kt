package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.model.Track
import org.junit.Test
import org.junit.Assert.assertEquals

class TrackUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any, track: Track) {
        val field = track.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(track), value)
    }

    @Test
    fun checkTrackFields() {
        val idValue = "1MVqeIAwhD4T44AKVkIfic"
        val albumValue = "leavemealone"
        val artistsValue = "Fred again.., Baby Keem"
        val minutesValue = 3
        val secondsValue = 42
        val imageValue = "https://i.scdn.co/image/ab67616d00001e02c9049966d5a1c1954ae98480"
        val nameValue = "leavemealone"
        val popularityValue = 79
        val releaseDateValue = "2023-12-08"
        val track = Track(idValue, albumValue, artistsValue, minutesValue, secondsValue, imageValue,
            nameValue, popularityValue, releaseDateValue)
        assertFieldEquals("id", idValue, track)
        assertFieldEquals("album", albumValue, track)
        assertFieldEquals("artists", artistsValue, track)
        assertFieldEquals("minutes", minutesValue, track)
        assertFieldEquals("seconds", secondsValue, track)
        assertFieldEquals("image", imageValue, track)
        assertFieldEquals("name", nameValue, track)
        assertFieldEquals("popularity", popularityValue, track)
        assertFieldEquals("releaseDate", releaseDateValue, track)
    }

}
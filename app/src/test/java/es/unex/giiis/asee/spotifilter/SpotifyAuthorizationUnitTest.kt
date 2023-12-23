package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.api.SpotifyAuthorization
import org.junit.Test
import org.junit.Assert.assertEquals

class SpotifyAuthorizationUnitTest {

    private fun assertFieldEquals(fieldName: String, value: Any,
        spotifyAuthorization: SpotifyAuthorization
    ) {
        val field = spotifyAuthorization.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        assertEquals("$fieldName fields didn't match", field.get(spotifyAuthorization),
            value)
    }

    @Test
    fun checkSpotifyAuthorizationFields() {
        val accessTokenValue = "123456789"
        val tokenTypeValue = "Bearer"
        val spotifyAuthorization = SpotifyAuthorization(accessTokenValue, tokenTypeValue)
        assertFieldEquals("accessToken", accessTokenValue, spotifyAuthorization)
        assertFieldEquals("tokenType", tokenTypeValue, spotifyAuthorization)
    }

}
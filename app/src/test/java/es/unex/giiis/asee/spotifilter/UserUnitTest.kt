package es.unex.giiis.asee.spotifilter

import es.unex.giiis.asee.spotifilter.data.model.User
import org.junit.Test
import org.junit.Assert.assertEquals

class UserUnitTest {

    @Test
    fun checkUserFields() {
        val idValue = (1).toLong()
        val nameValue = "admin"
        val passwordValue = "1234"
        val user = User(idValue, nameValue, passwordValue)
        val idField = user.javaClass.getDeclaredField("id")
        val nameField = user.javaClass.getDeclaredField("name")
        val passwordField = user.javaClass.getDeclaredField("password")
        idField.isAccessible = true
        nameField.isAccessible = true
        passwordField.isAccessible = true
        assertEquals("id fields didn't match", idField.get(user), idValue)
        assertEquals("name fields didn't match", nameField.get(user), nameValue)
        assertEquals("password fields didn't match", passwordField.get(user), passwordValue)
    }

}
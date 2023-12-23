package es.unex.giiis.asee.spotifilter.view

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.asee.spotifilter.TestUtils.performLogIn
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LogInTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LogInActivity::class.java)

    @Test
    fun logInTest() {
        performLogIn("admin", "1234")
    }

}
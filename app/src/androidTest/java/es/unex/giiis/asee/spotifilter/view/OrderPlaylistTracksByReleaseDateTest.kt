package es.unex.giiis.asee.spotifilter.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.TestUtils.performLogIn
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class OrderPlaylistTracksByReleaseDateTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LogInActivity::class.java)

    @Test
    fun orderPlaylistTracksByReleaseDateTest() {

        performLogIn("admin", "1234")

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.playlistsFragment), withContentDescription("Playlists"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(1000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.playlistsPlainText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("My playlist"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.playlistsButton), withText("Create new playlist"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        Thread.sleep(1000)

        val recyclerView = onView(
            allOf(
                withId(R.id.playlistsRecyclerView),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    3
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(1000)

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }

        }

    }

}
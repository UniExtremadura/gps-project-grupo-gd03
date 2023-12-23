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
class RemoveTrackFromPlaylistTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LogInActivity::class.java)

    @Test
    fun removeTrackFromPlaylistTest() {

        performLogIn("admin", "1234")

        Thread.sleep(2000)

        val recyclerView = onView(
            allOf(
                withId(R.id.tracksRecyclerView),
                childAtPosition(
                    withClassName(`is`("android.widget.FrameLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        Thread.sleep(1000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.addTrackToPlaylistPlainText),
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
                withId(R.id.addTrackToPlaylistButton), withText("Add to new playlist"),
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

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Desplazarse hacia arriba"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

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

        val recyclerView2 = onView(
            allOf(
                withId(R.id.playlistsRecyclerView),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    3
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(1000)

        val recyclerView3 = onView(
            allOf(
                withId(R.id.playlistDetailsRecyclerView),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    4
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

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
package es.unex.giiis.asee.spotifilter.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.TestUtils.performLogIn
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchTrackTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LogInActivity::class.java)

    @Test
    fun searchTrackTest() {

        performLogIn("admin", "1234")

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.actionSearch), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val searchAutoComplete = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_plate),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("badders"), closeSoftKeyboard())

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

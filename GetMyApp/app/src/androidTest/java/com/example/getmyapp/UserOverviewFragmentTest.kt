package com.example.getmyapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.getmyapp.database.User
import com.example.getmyapp.utils.utils
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserOverviewFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun userNotLoggedInTest() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        utils.removeLoginState(context)

        val actionMenuItemView = onView(
                allOf(withId(R.id.action_user), withContentDescription("User"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        onView(withId(R.id.loginHeaderTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun userLoggedInTest() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val user = User("-Ma2sFCW-WSiE9LCeH7l", "loginState", "",
                "", "loginState@mail.com", "",
                "4Biwq1iQIoSnPTEeD8qm0bxb1/vFhfItOECuzMWBjJw=", "d11vy0H4Cu2AK8l5NHNVuA==")

        utils.saveLoginState(context, user)

        val actionMenuItemView = onView(
                allOf(withId(R.id.action_user), withContentDescription("User"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        onView(withId(R.id.userOverviewTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.userOverviewUsernameInfoTextView)).check(matches(withText(user.name)))
        onView(withId(R.id.userOverviewMailInfoTextView)).check(matches(withText(user.mailAddress)))
    }

    @Test
    fun userSwitchTest() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val user = User("-Ma2sFCW-WSiE9LCeH7l", "loginState", "",
                "", "loginState@mail.com", "",
                "4Biwq1iQIoSnPTEeD8qm0bxb1/vFhfItOECuzMWBjJw=", "d11vy0H4Cu2AK8l5NHNVuA==")

        utils.saveLoginState(context, user)

        val actionMenuItemView = onView(
                allOf(withId(R.id.action_user), withContentDescription("User"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        onView(withId(R.id.userOverviewTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.userOverviewUsernameInfoTextView)).check(matches(withText(user.name)))
        onView(withId(R.id.userOverviewMailInfoTextView)).check(matches(withText(user.mailAddress)))

        val materialButton2 = onView(
                allOf(withId(R.id.userOverviewLogoutButton), withText("LOGOUT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                6),
                        isDisplayed()))
        materialButton2.perform(click())

        onView(withId(R.id.loginHeaderTextView)).check(matches(isDisplayed()))

        val appCompatEditText5 = onView(
                allOf(withId(R.id.usernameInputEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText5.perform(ViewActions.replaceText("loginState2"), ViewActions.closeSoftKeyboard())

        val appCompatEditText6 = onView(
                allOf(withId(R.id.passwordInputEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()))
        appCompatEditText6.perform(ViewActions.replaceText("geheim"), ViewActions.closeSoftKeyboard())

        val materialButton3 = onView(
                allOf(withId(R.id.loginButton), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                5),
                        isDisplayed()))
        materialButton3.perform(click())

        val actionMenuItemView3 = onView(
                allOf(withId(R.id.action_user), withContentDescription("User"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView3.perform(click())

        onView(withId(R.id.userOverviewTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.userOverviewUsernameInfoTextView)).check(matches(withText("loginState2")))
        onView(withId(R.id.userOverviewMailInfoTextView)).check(matches(withText("loginState2@mail.com")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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

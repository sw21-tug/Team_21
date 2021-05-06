package com.example.getmyapp

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class ChangelangFragmentUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun changeLanguageRussianToEnglish() {
        onView(withId(R.id.textViewFoundPets)).check(matches(withText("Found Pets")))
        onView(withId(R.id.textViewMissingPets)).check(matches(withText("Missing Pets")))
        val appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val navigationMenuItemView = onView(
                allOf(withId(R.id.nav_changelang),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()))
        navigationMenuItemView.perform(click())

        val materialButton = onView(
                allOf(withId(R.id.buttonRussian), withText("русский"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()))
        materialButton.perform(click())

        val appCompatImageButton2 = onView(
                allOf(withContentDescription("Открыть панель навигации"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        val navigationMenuItemView2 = onView(
                allOf(withId(R.id.nav_home),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()))
        navigationMenuItemView2.perform(click())

        onView(withId(R.id.textViewFoundPets)).check(matches(withText("Найденные домашние животные")))
        onView(withId(R.id.textViewMissingPets)).check(matches(withText("Пропавшие домашние животные")))

        val appCompatImageButton3 = onView(
                allOf(withContentDescription("Открыть панель навигации"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton3.perform(click())

        val navigationMenuItemView3 = onView(
                allOf(withId(R.id.nav_changelang),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()))
        navigationMenuItemView3.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.buttonEnglish), withText("English"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()))
        materialButton2.perform(click())

        val appCompatImageButton4 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton4.perform(click())

        val navigationMenuItemView4 = onView(
                allOf(withId(R.id.nav_home),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()))
        navigationMenuItemView4.perform(click())

        onView(withId(R.id.textViewFoundPets)).check(matches(withText("Found Pets")))
        onView(withId(R.id.textViewMissingPets)).check(matches(withText("Missing Pets")))
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
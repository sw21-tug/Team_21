package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.getmyapp.ui.login.RegisterFragment

import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.getmyapp.ui.missing.MissingFragment

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MissingFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun initMissingOverview() {
        val scenario = launchFragmentInContainer<MissingFragment> ()

        onView(withId(R.id.speciesSpinner)).check(matches(withSpinnerText("Select Species")))
        onView(withId(R.id.colorSpinner)).check(matches(withSpinnerText("Select Colour")))
        onView(withId(R.id.regionSpinner)).check(matches(withSpinnerText("Select Province")))

    }

    @Test
    fun incorrectInput() {
        val scenario = launchFragmentInContainer<RegisterFragment> ()

        onView(withId(R.id.usernameInputEditText)).perform(typeText("UserName_123"), closeSoftKeyboard())
        onView(withId(R.id.firstNameEditText)).perform(typeText("FirstName"), closeSoftKeyboard())
        onView(withId(R.id.lastNameEditText)).perform(typeText("LastName"), closeSoftKeyboard())
        onView(withId(R.id.mailAddressEditText)).perform(typeText("example@example.com"), closeSoftKeyboard())
        onView(withId(R.id.phoneNumberEditText)).perform(typeText("+43123456789"), closeSoftKeyboard())
        onView(withId(R.id.passwordInputEditText)).perform(typeText("secre"), closeSoftKeyboard())
        onView(withId(R.id.passwordInputEditText2)).perform(typeText("secreT"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())

        onView(withId(R.id.passwordInputEditText)).check(matches(withText("secre")))
        onView(withId(R.id.passwordInputEditText2)).check(matches(withText("secreT")))
    }
}
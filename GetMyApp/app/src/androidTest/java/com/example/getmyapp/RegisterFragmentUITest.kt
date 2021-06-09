package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.getmyapp.ui.login.RegisterFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.getmyapp.database.User

@RunWith(AndroidJUnit4::class)
class RegisterFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun correctInput() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer {
            RegisterFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_register)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.usernameInputEditText)).perform(typeText("UserName_123"), closeSoftKeyboard())
        onView(withId(R.id.firstNameEditText)).perform(typeText("FirstName"), closeSoftKeyboard())
        onView(withId(R.id.lastNameEditText)).perform(typeText("LastName"), closeSoftKeyboard())
        onView(withId(R.id.mailAddressEditText)).perform(typeText("example@example.com"), closeSoftKeyboard())
        onView(withId(R.id.phoneNumberEditText)).perform(typeText("+43123456789"), closeSoftKeyboard())
        onView(withId(R.id.passwordInputEditText)).perform(typeText("secreT"), closeSoftKeyboard())
        onView(withId(R.id.passwordInputEditText2)).perform(typeText("secreT"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click(), closeSoftKeyboard())

        var testUser: User? = null

        scenario.onFragment { fragment -> testUser = fragment.getTestUser("UserName_123")}

        assertNotNull("Test user is null", testUser)
        assertEquals("User name does not match", testUser?.name, "UserName_123")
        assertEquals("First name does not match", testUser?.firstName, "FirstName")
        assertEquals("Last name does not match", testUser?.lastName, "LastName")
        assertEquals("Mail address does not match", testUser?.mailAddress, "example@example.com")
        assertEquals("Phone number does not match", testUser?.phoneNumber, "+43123456789")

        scenario.onFragment { fragment -> fragment.deleteTestUser("UserName_123")}
    }

    @Test
    fun incorrectInput() {
        launchFragmentInContainer<RegisterFragment> ()

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
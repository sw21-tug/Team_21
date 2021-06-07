package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.getmyapp.ui.login.LoginFragment
import com.example.getmyapp.ui.login.RegisterFragment
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentUITest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun createTestUser(){
            val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

            launchFragmentInContainer {
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

            Espresso.onView(ViewMatchers.withId(R.id.usernameInputEditText))
                .perform(ViewActions.typeText("TestUserLogin"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.firstNameEditText))
                .perform(ViewActions.typeText("first"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.lastNameEditText))
                .perform(ViewActions.typeText("last"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.mailAddressEditText))
                .perform(ViewActions.typeText("example@example.com"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.phoneNumberEditText))
                .perform(ViewActions.typeText("+43123456"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText))
                .perform(ViewActions.typeText("secret"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText2))
                .perform(ViewActions.typeText("secret"), ViewActions.closeSoftKeyboard())

            Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click(), ViewActions.closeSoftKeyboard())
        }
        @AfterClass
        @JvmStatic
        fun deleteTestUser(){
            val scenario = launchFragmentInContainer<RegisterFragment>()
            scenario.onFragment { fragment -> fragment.deleteTestUser("TestUserLogin")}
        }
    }
    @Test
    @Throws(Exception::class)
    fun correctInput() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        launchFragmentInContainer {
            LoginFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_login)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        Espresso.onView(ViewMatchers.withId(R.id.usernameInputEditText))
            .perform(ViewActions.typeText("TestUserLogin"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText))
            .perform(ViewActions.typeText("secret"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .perform(ViewActions.click(), ViewActions.closeSoftKeyboard())
    }

    @Test
    fun incorrectUser() {

        launchFragmentInContainer<LoginFragment> ()

        Espresso.onView(ViewMatchers.withId(R.id.usernameInputEditText))
            .perform(ViewActions.typeText("IncorrectUser"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText))
            .perform(ViewActions.typeText("secret"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .perform(ViewActions.click(), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.usernameInputEditText)).check(matches(ViewMatchers.hasErrorText("User does not exist")))
    }

    @Test
    fun incorrectPassword() {

        launchFragmentInContainer<LoginFragment> ()

        Espresso.onView(ViewMatchers.withId(R.id.usernameInputEditText))
            .perform(ViewActions.typeText("TestUserLogin"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText))
            .perform(ViewActions.typeText("IncorrectPassword"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .perform(ViewActions.click(), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordInputEditText)).check(matches(ViewMatchers.hasErrorText("Incorrect Password")))
    }

}
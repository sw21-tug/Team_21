package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.getmyapp.database.User
import com.example.getmyapp.ui.found.FoundFragment
import com.example.getmyapp.utils.utils
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoundFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun initMissingOverview() {
        launchFragmentInContainer<FoundFragment>(themeResId = R.style.Theme_AppCompat_Light)

        onView(withId(R.id.speciesSpinner)).check(matches(withSpinnerText("Select Species")))
        onView(withId(R.id.colorSpinner)).check(matches(withSpinnerText("Select Colour")))
        onView(withId(R.id.regionSpinner)).check(matches(withSpinnerText("Select Province")))
    }

    @Test
    fun createFoundReportTest() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val user = User("-Ma2sFCW-WSiE9LCeH7l", "loginState", "",
                "", "loginState@mail.com", "",
                "4Biwq1iQIoSnPTEeD8qm0bxb1/vFhfItOECuzMWBjJw=", "d11vy0H4Cu2AK8l5NHNVuA==")

        utils.saveLoginState(context, user)

        launchFragmentInContainer(themeResId = R.style.Theme_AppCompat_Light) {
            FoundFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_found)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.addFoundPetButton)).perform(click())

        assert(navController.currentDestination?.id == R.id.nav_add_report)
    }
}
package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.action.ViewActions.click
import com.example.getmyapp.ui.missing.MissingFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MissingFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun initMissingOverview() {
        launchFragmentInContainer<MissingFragment>(themeResId = R.style.Theme_AppCompat_Light)

        onView(withId(R.id.speciesSpinner)).check(matches(withSpinnerText("Select Species")))
        onView(withId(R.id.colorSpinner)).check(matches(withSpinnerText("Select Colour")))
        onView(withId(R.id.regionSpinner)).check(matches(withSpinnerText("Select Province")))

    }

    @Test
    fun createMissingReportTest() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer(themeResId = R.style.Theme_AppCompat_Light) {
            MissingFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_missing)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.addMissingPetButton)).perform(click())

        assert(navController.currentDestination?.id == R.id.nav_add_report)
    }
}
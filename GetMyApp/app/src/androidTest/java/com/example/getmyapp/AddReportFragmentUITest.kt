package com.example.getmyapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.getmyapp.ui.report.AddReportFragment
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddReportFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun initAddPetFragmentTest() {
        launchFragmentInContainer<AddReportFragment>()

        onView(withId(R.id.createReportSpeciesSpinner)).check(matches(withSpinnerText("Select Species")))
    }

    @Test
    fun createCorrectFoundReportTest() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer(bundleOf("found" to true)) {
            AddReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_add_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.createReportNameEditText)).perform(typeText("Test Found Pet"), closeSoftKeyboard())
        onView(withId(R.id.createReportSpeciesSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())
        onView(withId(R.id.createReportBreedEditText)).perform(typeText("Australian Shepherd"), closeSoftKeyboard())
        onView(withId(R.id.createReportColourEditText)).perform(typeText("Grey"), closeSoftKeyboard())
        onView(withId(R.id.createReportAgeEditText)).perform(typeText("18"), closeSoftKeyboard())
        onView(withId(R.id.createReportRegionEditText)).perform(typeText("Styria"), closeSoftKeyboard())
        onView(withId(R.id.createReportGenderEditText)).perform(typeText("Male"), closeSoftKeyboard())
        onView(withId(R.id.createReportLastSeenEditText)).perform(typeText("01.01.2021"), closeSoftKeyboard())
        onView(withId(R.id.createReportChipNumberEditText)).perform(typeText("123456789"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        assert(navController.currentDestination?.id == R.id.nav_found)
    }

    @Test
    fun createIncorrectFoundReportTest() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer(bundleOf("found" to true)) {
            AddReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_add_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.saveButton)).perform(click())

        val errorText = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(R.string.generic_error)

        onView(withId(R.id.createReportBreedEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportColourEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportAgeEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportRegionEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportGenderEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportLastSeenEditText)).check(matches(hasErrorText(errorText)))

        assert(navController.currentDestination?.id == R.id.nav_add_report)
    }

    @Test
    fun createCorrectMissingReportTest() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer(bundleOf("found" to false)) {
            AddReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_add_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.createReportNameEditText)).perform(typeText("Test Missing Pet"), closeSoftKeyboard())
        onView(withId(R.id.createReportSpeciesSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())
        onView(withId(R.id.createReportBreedEditText)).perform(typeText("Australian Shepherd"), closeSoftKeyboard())
        onView(withId(R.id.createReportColourEditText)).perform(typeText("Grey"), closeSoftKeyboard())
        onView(withId(R.id.createReportAgeEditText)).perform(typeText("18"), closeSoftKeyboard())
        onView(withId(R.id.createReportRegionEditText)).perform(typeText("Styria"), closeSoftKeyboard())
        onView(withId(R.id.createReportGenderEditText)).perform(typeText("Male"), closeSoftKeyboard())
        onView(withId(R.id.createReportLastSeenEditText)).perform(typeText("01.01.2021"), closeSoftKeyboard())
        onView(withId(R.id.createReportChipNumberEditText)).perform(typeText("123456789"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        assert(navController.currentDestination?.id == R.id.nav_missing)
    }

    @Test
    fun createIncorrectMissingReportTest() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer(bundleOf("found" to false)) {
            AddReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_add_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.saveButton)).perform(click())

        val errorText = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(R.string.generic_error)

        onView(withId(R.id.createReportNameEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportBreedEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportColourEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportAgeEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportRegionEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportGenderEditText)).check(matches(hasErrorText(errorText)))
        onView(withId(R.id.createReportLastSeenEditText)).check(matches(hasErrorText(errorText)))
        //onView(withId(R.id.createReportChipNumberEditText)).check(matches(hasErrorText(errorText)))

        assert(navController.currentDestination?.id == R.id.nav_add_report)
    }
}
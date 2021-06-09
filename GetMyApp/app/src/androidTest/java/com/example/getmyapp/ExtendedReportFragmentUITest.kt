package com.example.getmyapp

import android.os.SystemClock
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.getmyapp.database.User
import com.example.getmyapp.ui.extendedreport.ExtendedReportFragment
import com.example.getmyapp.utils.utils
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.Throws

@RunWith(AndroidJUnit4::class)
class ExtendedReportFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun noUser() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

        launchFragmentInContainer {
            ExtendedReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_extended_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEmail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonPhone)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonMessage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }


    @Test
    @Throws(Exception::class)
    fun userLoggedIn() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val user = User("-Mbk0k0kjMC3fNUA0IPf", "TestUserLogin2", "first",
                "last", "", "+43123456",
            "TKb28Mq34092bvFkAdJy4DKFKbnyzML4hNXK/Q6H6is=", "VccTskbcTbli48F0Te4S6A==")

        utils.saveLoginState(context, user)

        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

        launchFragmentInContainer(bundleOf("age" to "7",
            "breed" to "Dog", "chipNo" to "123469",
            "color" to "Black", "gender" to "Male",
            "lastSeen" to "12.07.19", "name" to "Justus",
            "region" to "Graz", "species" to "Labrador",
            "ownerID" to "-Mbk0k0kjMC3fNUA0IPf",
            "petId" to "-MabXlFqdLCgwGrybceN")) {
            ExtendedReportFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.mobile_navigation)
                        navController.setCurrentDestination(R.id.nav_extended_report)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        SystemClock.sleep(1500)

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonMessage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEmail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonPhone)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
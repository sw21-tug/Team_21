package com.example.getmyapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.getmyapp.database.User
import com.example.getmyapp.ui.extendedreport.ExtendedReportFragment
import com.example.getmyapp.ui.login.LoginFragment
import com.example.getmyapp.ui.login.RegisterFragment
import com.example.getmyapp.ui.missing.MissingFragment
import com.example.getmyapp.ui.report.AddReportFragment
import com.example.getmyapp.utils.utils
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.Throws

@RunWith(AndroidJUnit4::class)
class ExtendedReportFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun NoUser() {
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<ExtendedReportFragment> {
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
    fun UserLoggedIn() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val user = User("-MbB7C0rh5-uiL5KCFmc", "TestUserLogin2", "first",
                "last", "example@example.com", "+43123456",
                "YwgIHHfdfFPIkcBtryYO7wEz9mAojpQ7XYhq8K1M+WE=", "aX85ngqEi5ah7a43FAEzVA==")

        utils.saveLoginState(context, user)

        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer(bundleOf("age" to "7",
            "breed" to "Dog", "chipNo" to "123469",
            "color" to "Black", "gender" to "Male",
            "lastSeen" to "12.07.19", "name" to "Justus",
            "region" to "Graz", "species" to "Labrador",
            "ownerID" to "-MbB7C0rh5-uiL5KCFmc")) {
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



        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEmail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonPhone)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonMessage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
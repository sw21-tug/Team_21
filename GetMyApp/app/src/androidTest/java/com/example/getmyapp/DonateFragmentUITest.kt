package com.example.getmyapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.getmyapp.ui.iap.InAppPurchaseFragment

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DonateFragmentUITest {
    @Test
    @Throws(Exception::class)
    fun donateButtonFunctionality() {
        launchFragmentInContainer<InAppPurchaseFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.buttonDonate))
            .check(ViewAssertions.matches(ViewMatchers.withText("Donate")))
        Espresso.onView(ViewMatchers.withId(R.id.buttonDonate))
            .perform(ViewActions.click())
    }
}
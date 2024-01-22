package com.example.gamesbooks

import android.provider.Settings
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions

import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gamesbooks.presentation.bookscreen.GameBookActivity
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameBookActivityInstrumentedTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(GameBookActivity::class.java)

    // Create an idling resource to wait for asynchronous tasks
    private val countingIdlingResource = CountingIdlingResource("Network_Call")

    @Before
    fun setUp() {
        // Disable animations
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val animationStatus = Settings.Global.getInt(
            instrumentation.targetContext.contentResolver,
            Settings.Global.WINDOW_ANIMATION_SCALE,
            0
        )
        Settings.Global.putInt(
            instrumentation.targetContext.contentResolver,
            Settings.Global.WINDOW_ANIMATION_SCALE,
            0
        )
        instrumentation.uiAutomation.executeShellCommand("settings put global transition_animation_scale 0")
        instrumentation.uiAutomation.executeShellCommand("settings put global animator_duration_scale 0")
        instrumentation.uiAutomation.executeShellCommand("settings put secure window_animation_scale 0")
    }

    @Test
    fun clickSideDrawerItem_ShowsBookData() {
        // Wait for the activity to launch
        Thread.sleep(2000)

        // Click on the side drawer icon to open the drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        // Wait for the character data to load (adjust sleep time based on your scenario)
        Thread.sleep(5000)

        // Check if the character data is displayed (you may need to adjust the view ID)
        onView(withId(R.id.char_rv_view)).check(matches(isDisplayed()))


    }


    @Test
    fun clickSideDrawerItem_ShowsCharacterData() {
        // Register the idling resource before the test
        countingIdlingResource.increment()
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        Thread.sleep(10000)

        // Click on the desired side drawer item (e.g., the first item)
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(1))


        // Wait for the character data to load using idling resource
        countingIdlingResource.wait()
        Thread.sleep(15000)

        // Check if the character data is displayed (you may need to adjust the view ID)
        onView(withId(R.id.char_rv_view)).check(matches(isDisplayed()))
    }
}

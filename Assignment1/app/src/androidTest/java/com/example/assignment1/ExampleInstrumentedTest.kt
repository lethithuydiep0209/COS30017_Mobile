package com.example.assignment1

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test //Kiểm tra xem package name của ứng dụng
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.assignment1", appContext.packageName)
    }

    @Test //Kiểm tra tăng điểm khi leo (Climb
    fun testClimbButton_IncrementsScore() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Nhấn nút Climb
        onView(withId(R.id.climbButton)).perform(click())

        // Kiểm tra điểm số tăng lên 1
        onView(withId(R.id.scoreTextView)).check(matches(withText("1")))
    }

    @Test //Kiểm tra giảm điểm khi rơi (Fall)
    fun testFallButton_DecreasesScore() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Nhấn nút Climb 2 lần
        onView(withId(R.id.climbButton)).perform(click()).perform(click())

        // Nhấn nút Fall
        onView(withId(R.id.fallButton)).perform(click())

        // Kiểm tra điểm số bị trừ (tối thiểu là 0)
        onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
    }

    @Test //Kiểm tra reset điểm (Reset)
    fun testResetButton_ResetsScore() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Nhấn Climb 3 lần
        onView(withId(R.id.climbButton)).perform(click()).perform(click()).perform(click())

        // Nhấn Reset
        onView(withId(R.id.resetButton)).perform(click())

        // Kiểm tra điểm số trở về 0
        onView(withId(R.id.scoreTextView)).check(matches(withText("0")))
    }
}

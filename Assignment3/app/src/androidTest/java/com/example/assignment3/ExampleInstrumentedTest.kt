package com.example.assignment3

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(AddTodoActivity::class.java)

    //  Test 1: Nút Save hiển thị
    @Test
    fun saveButtonIsDisplayed() {
        onView(withId(R.id.btnSave))
            .check(matches(isDisplayed()))
    }

    //  Test 2: Nhập tiêu đề và kiểm tra text
    @Test
    fun inputTitleText() {
        onView(withId(R.id.editTitle))
            .perform(typeText("Buy milk"), closeSoftKeyboard())

        onView(withId(R.id.editTitle))
            .check(matches(withText("Buy milk")))
    }

    //  Test 3: Checkbox hiển thị và không được tick sẵn
    @Test
    fun checkboxIsDisplayedAndUnchecked() {
        onView(withId(R.id.checkboxDone))
            .check(matches(isDisplayed()))
            .check(matches(isNotChecked()))
    }

    // Test 4: Nhập mô tả
    @Test
    fun inputDescriptionText() {
        onView(withId(R.id.editDescription))
            .perform(typeText("Buy at local store"), closeSoftKeyboard())

        onView(withId(R.id.editDescription))
            .check(matches(withText("Buy at local store")))
    }
}

package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.faust.m.td.R
import org.hamcrest.CoreMatchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SelectActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SelectActivity> = ActivityTestRule(SelectActivity::class.java)


    @Test
    fun clickAnyNonFirstItemOnListShouldDisplayDialogWithCorrectTitle() {
        clickItemOnListAtPosition(2)

        // Assert that a view with a correct title text is displayed (which is an approximation of
        // asserting that a dialog with correct title is displayed)
        onView(withText(R.string.select_ac_dialog_translate_title))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    private fun clickItemOnListAtPosition(pPosition: Int) {
        onData(anything())
            .inAdapterView(withId(R.id.selectListViewSentences))
            .atPosition(pPosition)
            .perform(click())
    }
}
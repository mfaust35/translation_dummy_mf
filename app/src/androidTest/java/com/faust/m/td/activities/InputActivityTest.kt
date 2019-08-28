package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.faust.m.td.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class InputActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(InputActivity::class.java)

    @Test
    fun clickOnButtonCancelShouldFinishActivity() {
        onView(withId(R.id.cancel_button)).perform(click())

        assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun clickOnButtonAddShouldInsertTranslationIntoDao() {
        onView(withId(R.id.sentence_edit_text)).perform(replaceText("That!"))
        onView(withId(R.id.add_button)).perform(click())

        // TODO: In order to test insertion into dao, I need to get a mock translationDao
        // For that, I need to use injection dependencies I suppose
        assertTrue(activityTestRule.activity.isFinishing)
    }
}
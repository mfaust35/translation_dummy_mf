package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.R
import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.domain.Translation
import io.mockk.*
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4ClassRunner::class)
class InputActivityTest : KoinTest {

    // Apparently, this  mock is being recreated for each test, so there should be no problems
    // of tests influencing each other. Check why / who is recreating mock instances
    private var translationDS: TranslationDataSource = mockk()

    @get:Rule
    var activityTestRule = KoinActivityTestRule(
        InputActivity::class.java,
        module { single { translationDS } }
    )

    @Test
    fun clickOnButtonCancelShouldFinishActivity() {
        onView(withId(R.id.cancel_button)).perform(click())

        assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun clickOnButtonAddShouldFinishActivity() {
        clickOnButtonAddAfterInputSentence("This")

        assertTrue(activityTestRule.activity.isFinishing)
    }
    private fun clickOnButtonAddAfterInputSentence(sentence: String) {
        every { translationDS.addTranslation(any()) } just Runs

        onView(withId(R.id.sentence_edit_text)).perform(replaceText(sentence))
        onView(withId(R.id.add_translation_button)).perform(click())
    }


    @Test
    fun clickOnButtonAddShouldInsertTranslationIntoDao() {
        // Given sentence_edit_text contain text "That"
        // When click on button add
        clickOnButtonAddAfterInputSentence("This")

        // Then translationDS insert a new translation with correct attributes
        //verifyAll { translationDS.addTranslation(match { it.english == "This" }) }
        verifyAll { translationDS.addTranslation(Translation("This", "unknown!", DEFAULT_USER_ID, 0)) }
    }
}
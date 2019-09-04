package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.faust.m.td.R
import com.faust.m.td.koin.KoinActivityTestRule
import com.faust.m.td.translation.TranslationDao
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class InputActivityTest : KoinTest {

    // Apparently, this  mock is being recreated for each test, so there should be no problems
    // of tests influencing each other. Check why / who is recreating mock instances
    private var translationDao: TranslationDao = mock(TranslationDao::class.java)

    @get:Rule
    var activityTestRule = KoinActivityTestRule(
        InputActivity::class.java,
        module { single { translationDao  } }
    )

    @Test
    fun clickOnButtonCancelShouldFinishActivity() {
        onView(withId(R.id.cancel_button)).perform(click())

        assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun clickOnButtonAddShouldFinishActivity() {
        clickOnButtonAddAfterInputSentence("That")

        assertTrue(activityTestRule.activity.isFinishing)
    }
    private fun clickOnButtonAddAfterInputSentence(sentence: String) {
        doNothing().whenever(translationDao).insertAll(any())

        onView(withId(R.id.sentence_edit_text)).perform(replaceText(sentence))
        onView(withId(R.id.add_translation_button)).perform(click())
    }

    @Test
    fun clickOnButtonAddShouldInsertTranslationIntoDao() {
        clickOnButtonAddAfterInputSentence("That")

        verify(translationDao).insertAll(argThat { english == "That" })
    }
}
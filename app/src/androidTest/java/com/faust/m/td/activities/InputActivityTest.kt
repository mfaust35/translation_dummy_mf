package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.R
import com.faust.m.td.koin.KoinActivityTestRule
import com.faust.m.td.framework.db.TranslationEntity
import com.faust.m.td.framework.db.TranslationDao
import com.faust.m.td.presentation.input.InputActivity
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4ClassRunner::class)
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
        clickOnButtonAddAfterInputSentence("This")

        assertTrue(activityTestRule.activity.isFinishing)
    }
    private fun clickOnButtonAddAfterInputSentence(sentence: String) {
        doNothing().whenever(translationDao).insertAll(any())

        onView(withId(R.id.sentence_edit_text)).perform(replaceText(sentence))
        onView(withId(R.id.add_translation_button)).perform(click())
    }


    @Test
    fun clickOnButtonAddShouldInsertTranslationIntoDao() {
        // Given sentence_edit_text contain text "That"
        // When click on button add
        clickOnButtonAddAfterInputSentence("That")

        // Then translationDao insert a new translation with correct attributes
        argumentCaptor<TranslationEntity>().apply {
            verify(translationDao).insertAll(capture())
            assertThat(firstValue)
                .isEqualTo(
                    TranslationEntity(
                        "That",
                        "unknown!",
                        DEFAULT_USER_ID,
                        0
                    )
                )
        }
    }
}
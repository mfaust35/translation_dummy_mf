package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.faust.m.td.R
import com.faust.m.td.koin.KoinIntentsTestRule
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationDao
import com.faust.m.td.translation.TranslationRecyclerAdapter
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class SelectActivityTest : KoinTest {

    private var translationDao: TranslationDao = mock(TranslationDao::class.java)

    @get:Rule
    var intentsTestRule = KoinIntentsTestRule(
        SelectActivity::class.java,
        module { single { translationDao } })
    // Add lambda to stub translationDao before activity is launched because
    // `getAll()` is called during onResume()
    { whenever(translationDao.getAll()).thenReturn(listOf(Translation("en", "fr"))) }


    // TODO : these tests are leaking dialog window. I don't know why
    // try replacing showDialog by a dialogFragment ??
    // https://stackoverflow.com/questions/9059116/activity-has-leaked-window-dialog-this-again
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
        // Click on the RecyclerView item at position 2
        onView(withId(R.id.recyclerViewTranslation))
            .perform(actionOnItemAtPosition<TranslationRecyclerAdapter.TranslationHolder>(pPosition, click()))
    }

    @Test
    fun clickFirstItemOnListShouldSendIntentStartActivityInput() {
        clickItemOnListAtPosition(0)

        // Assert that activity input has been started
        intended(hasComponent(InputActivity::class.java.name))
    }
}
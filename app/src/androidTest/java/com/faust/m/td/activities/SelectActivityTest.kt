package com.faust.m.td.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.R
import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.data.UserDataSource
import com.faust.m.td.domain.Translation
import com.faust.m.td.domain.User
import com.faust.m.td.translation.TranslationRecyclerAdapter.TranslationHolder
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4ClassRunner::class)
class SelectActivityTest : KoinTest {

    private val user = User("moi", 10)
    private val translation = Translation("en", "fr", user.id)

    private var translationDS: TranslationDataSource = mockk()
    private var userDS: UserDataSource = mockk()

    @get:Rule
    var intentsTestRule = KoinIntentsTestRule(
        SelectActivity::class.java,
        module {
            single { translationDS }
            single { userDS }
        }
    ) {
        // Add lambda to stub translationDao before activity is launched because
        // `getAll()` is called during onResume()
        every { translationDS.getAllTranslations() } returns listOf(translation)
        every { userDS.getUserForId(DEFAULT_USER_ID) } returns user
    }


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
            .perform(actionOnItemAtPosition<TranslationHolder>(pPosition, click()))
    }

    @Test
    fun clickFirstItemOnListShouldSendIntentStartActivityInput() {
        clickItemOnListAtPosition(0)

        // Assert that activity input has been started
        intended(hasComponent(InputActivity::class.java.name))
    }
}
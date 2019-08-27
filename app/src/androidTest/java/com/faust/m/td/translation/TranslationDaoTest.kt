package com.faust.m.td.translation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.faust.m.td.TranslationDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class TranslationDaoTest :AnkoLogger {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: TranslationDatabase
    private lateinit var translationDao: TranslationDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        try {
            database = Room.inMemoryDatabaseBuilder(context, TranslationDatabase::class.java)
                .allowMainThreadQueries().build()
        } catch (e: Exception) {
            info { "test ${e.message}" }
        }
        translationDao = database.translationDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAddingData() {
        val countPreInsert = translationDao.getAll().count()

        val translation = Translation("hello", "bonjour")
        translationDao.insertAll(translation)

        val countPostInsert = translationDao.getAll().count()
        assertEquals(1, countPostInsert-countPreInsert,
            "Difference between before and after insert should be '1'")
    }

    @Test
    fun testRetrieveData() {
        val translation = Translation("hello", "bonjour")
        translationDao.insertAll(translation)

        val firstTranslation = translationDao.getAll().firstOrNull()
        assertEquals("hello",
            firstTranslation?.english ?: "Translation unexisting",
            "Translation should be the same")
    }
}
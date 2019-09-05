package com.faust.m.td.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

/**
 * Setup and tear down code for any xxxDaoTest
 */
abstract class BaseDaoTest {

    private lateinit var database: TranslationDatabase

    @Before
    fun setup() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TranslationDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        onDatabaseCreated(database)
    }

    abstract fun onDatabaseCreated(database: TranslationDatabase)

    @After
    fun tearDown() {
        database.close()
    }

}
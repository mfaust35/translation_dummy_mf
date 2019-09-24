package com.faust.m.td.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.faust.m.td.framework.RoomTranslationDatabase
import org.junit.After
import org.junit.Before

/**
 * Setup and tear down code for any xxxDaoTest
 */
abstract class BaseDaoTest {

    private lateinit var database: RoomTranslationDatabase

    @Before
    fun setup() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RoomTranslationDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        onDatabaseCreated(database)
    }

    abstract fun onDatabaseCreated(database: RoomTranslationDatabase)

    @After
    fun tearDown() {
        database.close()
    }

}
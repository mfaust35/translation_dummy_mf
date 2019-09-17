package com.faust.m.td.database

import android.app.Instrumentation
import android.util.Log
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.room.RoomOpenHelper
import androidx.room.migration.bundle.DatabaseBundle
import androidx.room.migration.bundle.SchemaBundle
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.FileNotFoundException
import java.lang.ref.WeakReference

private const val TAG = "CreationTestHelper"

/**
 * A class that can be used in Instrumentation tests to create a database in an older schema and
 * add callback to creation. It is useful for testing pre-populate callback
 *
 * Implementation is copied/adapted from [androidx.room.testing.MigrationTestHelper]
 */
class CreationTestHelper(private val mInstrumentation: Instrumentation,
                         private val mAssetsFolder: String?,
                         private val mOpenFactory: SupportSQLiteOpenHelper.Factory) : TestWatcher() {

    private val mManagedDatabases =
        mutableListOf<WeakReference<SupportSQLiteDatabase>>()

    fun createDatabase(name: String, version: Int, callbacks: List<Callback>):SupportSQLiteDatabase {
        mInstrumentation.targetContext.getDatabasePath(name).let { when { it.exists() -> {
            Log.d(TAG, "Deleting database file $name")
            check(it.delete()) { ("There is a database file and I could not delete"
                    + " it. Make sure you don't have any open connections to that database"
                    + " before calling this method.") }
            }
        } }
        val configuration = DatabaseConfiguration(
            mInstrumentation.targetContext, name, mOpenFactory, RoomDatabase.MigrationContainer(),
            null, true, RoomDatabase.JournalMode.TRUNCATE,
            ArchTaskExecutor.getIOThreadExecutor(), ArchTaskExecutor.getIOThreadExecutor(),
            false, true,
            false, emptySet()
        )
        val roomOpenHelper = with(databaseBundleFor(version)) {
            RoomOpenHelper(configuration, CreatingDelegate(this, callbacks),
                this.identityHash, this.identityHash)
                // we pass the same hash twice since an old schema does not necessarily have
                // a legacy hash and we would not even persist it.
        }
        return openDatabase(name, roomOpenHelper)
    }

    private fun databaseBundleFor(version: Int): DatabaseBundle {
        return try {
            mInstrumentation.context.assets.open("$mAssetsFolder/$version.json").let {
                SchemaBundle.deserialize(it).database
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("Cannot find the schema file in the assets folder. Make " +
                    "sure to include the exported json schemas in your test assert inputs. See " +
                    "https://developer.android.com/topic/libraries/architecture/" +
                    "room.html#db-migration-testing for details. Missing file: ${e.message}")
        }
    }

    private fun openDatabase(name:String, roomOpenHelper: RoomOpenHelper): SupportSQLiteDatabase {
        val config = SupportSQLiteOpenHelper.Configuration
            .builder(mInstrumentation.targetContext)
            .callback(roomOpenHelper)
            .name(name)
            .build()
        return mOpenFactory.create(config).writableDatabase.also {
            mManagedDatabases.add(WeakReference(it))
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        println("We are finishing all database")
        mManagedDatabases.forEach { weakReference ->
            weakReference.get()?.apply { when { isOpen -> close()} }
        }
    }

    private inner class CreatingDelegate(private val mDatabaseBundle: DatabaseBundle,
                                         private val mCallbacks: List<Callback>) :
        RoomOpenHelper.Delegate(mDatabaseBundle.version) {

        override fun onCreate(database: SupportSQLiteDatabase?) {
            database?.apply {
                mCallbacks.forEach { it.onCreate(database) }
            }
        }

        override fun onOpen(database: SupportSQLiteDatabase?) { }

        override fun createAllTables(database: SupportSQLiteDatabase?) =
            mDatabaseBundle.buildCreateQueries().forEach { database?.execSQL(it) }

        override fun dropAllTables(database: SupportSQLiteDatabase?) =
            throw UnsupportedOperationException("Cannot drop all tables in the test")

        override fun validateMigration(db: SupportSQLiteDatabase?) =
            throw UnsupportedOperationException("This open helper just creates the database but" +
                    " it received a migration request.")
    }
}
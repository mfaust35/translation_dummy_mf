package com.faust.m.td.translation

import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TranslationRepository(private val translationDao: TranslationDao) {

    private var data: LiveData<List<Translation>>

    init {
        println("Initialize the repository and actually get the data")
        data = translationDao.getAll()
    }

    fun getAllTranslation(): LiveData<List<Translation>> {
        println("TranslationRepository returns the translation")
        return data
    }

    fun insert(translation: Translation) {
        InsertAsyncTask().execute(translation)
    }

    private inner class InsertAsyncTask : AsyncTask<Translation, Unit, Unit>() {

        override fun doInBackground(vararg p0: Translation?) {

            val translation = p0[0]
            if (translation != null)
                translationDao.insertAll(translation)
        }
    }
}
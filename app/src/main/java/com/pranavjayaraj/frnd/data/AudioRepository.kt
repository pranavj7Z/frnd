package com.pranavjayaraj.frnd.data

import android.app.Application
import android.os.AsyncTask
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioRepository(application: Application) {

    private var audioDao: AudioDao

    private var allAudio: LiveData<List<Audio>>

    init {
        val database: AudioDatabase = AudioDatabase.getInstance(
            application.applicationContext
        )!!
        audioDao = database.audioDao()
        allAudio = audioDao.getAllAudio()
    }
        fun getAllAudio(): LiveData<List<Audio>> {
        return audioDao.getAllAudio()
    }

    fun insert(audio: Audio) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(audioDao).execute(audio)
    }
    companion object {
        private class InsertNoteAsyncTask(audioDao: AudioDao) :
            AsyncTask<Audio, Unit, Unit>() {
            val audioDao = audioDao

                override fun doInBackground(vararg p0: Audio) {
                audioDao.insert(p0[0]!!)
            }
        }
    }

    }
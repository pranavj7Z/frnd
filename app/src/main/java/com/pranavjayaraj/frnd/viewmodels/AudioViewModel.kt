package com.pranavjayaraj.frnd
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pranavjayaraj.frnd.data.Audio
import com.pranavjayaraj.frnd.data.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: AudioRepository =
        AudioRepository(application)
    private var allAudio: LiveData<List<Audio>> = repository.getAllAudio()


    fun getAllAudio(): LiveData<List<Audio>> {
        return allAudio
    }

    fun insert(audio: Audio) {
        repository.insert(audio)
    }
}
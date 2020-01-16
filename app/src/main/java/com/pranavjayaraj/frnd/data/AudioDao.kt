package com.pranavjayaraj.frnd.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AudioDao {

    @Insert
    fun insert(audio: Audio)

    @Update
    fun update(audio: Audio)

    @Delete
    fun delete(audio: Audio)

    @Query("DELETE FROM audio_table")
    fun deleteAllAudio()

    @Query("SELECT * FROM audio_table")
    fun getAllAudio(): LiveData<List<Audio>>

}
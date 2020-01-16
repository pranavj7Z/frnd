package com.pranavjayaraj.frnd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Audio::class], version = 1)
abstract class AudioDatabase : RoomDatabase() {

    abstract fun audioDao(): AudioDao


    companion object {
        private var instance: AudioDatabase? = null

        fun getInstance(context: Context): AudioDatabase? {
            if (instance == null) {
                synchronized(AudioDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AudioDatabase::class.java, "audio_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }

}
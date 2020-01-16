package com.pranavjayaraj.frnd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_table")
data class Audio(

    var audioPath: String
)
{

    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
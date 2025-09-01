// In: data/Lap.kt
package com.rahul.auric.auricsnap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laps")
data class Lap(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lapTime: Long,
    val totalTime: Long
)
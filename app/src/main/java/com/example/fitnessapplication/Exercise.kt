package com.example.fitnessapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,

    val title: String,
    var sets: MutableList<Set>
)



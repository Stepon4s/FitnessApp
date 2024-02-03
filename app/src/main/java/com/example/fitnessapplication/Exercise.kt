package com.example.fitnessapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    val title: String,
    var sets: MutableList<Set>,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)



package com.example.fitnessapplication.workoutDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    val startTime: Long,
    val endTime: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

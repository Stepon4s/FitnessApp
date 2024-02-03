package com.example.fitnessapplication.ExerciseNameDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseName(

    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
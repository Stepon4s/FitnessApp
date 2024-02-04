package com.example.fitnessapplication.workoutDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Exercise(
    val title: String,
    val workoutId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)



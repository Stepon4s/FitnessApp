package com.example.fitnessapplication.workoutDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Exercise::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )])
data class Set (
    val number: Int,
    val reps: Int,
    val weight: Double,
    val exerciseId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
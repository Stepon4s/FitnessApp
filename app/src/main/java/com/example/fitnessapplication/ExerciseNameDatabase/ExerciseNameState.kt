package com.example.fitnessapplication.ExerciseNameDatabase

import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseName

data class ExerciseNameState(
    val exerciseNames: List<ExerciseName> = emptyList(),
    val title: String = "",
    val isAddingExerciseName: Boolean = false
)

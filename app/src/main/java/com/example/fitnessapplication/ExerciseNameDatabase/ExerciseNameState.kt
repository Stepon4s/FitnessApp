package com.example.fitnessapplication.ExerciseNameDatabase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ExerciseNameState(
    val exerciseNames: Flow<List<ExerciseName>> = emptyFlow(),
    val title: String = "",
    val isAddingExerciseName: Boolean = false
)

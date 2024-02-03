package com.example.fitnessapplication.ExerciseNameDatabase

sealed interface ExerciseNameEvent {
    object SaveExerciseName: ExerciseNameEvent
    data class setTitle(val title: String) : ExerciseNameEvent
    object ShowDialog: ExerciseNameEvent
    object HideDialog: ExerciseNameEvent
    data class SortExercisesNames(val sortType: String) : ExerciseNameEvent
    data class DeleteExerciseName(val exerciseName: ExerciseName) : ExerciseNameEvent
}
package com.example.fitnessapplication.ExerciseNameDatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseNameViewModel(
    private val dao: ExerciseNameDao
): ViewModel(){

    private val _exerciseNames = dao.getExerciseNames()
    private val _state = MutableStateFlow(ExerciseNameState())
    val state = combine(_state,_exerciseNames) { state, exerciseNames ->
        state.copy(
            exerciseNames = exerciseNames
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseNameState())

    fun onEvent(event: ExerciseNameEvent) {
        when(event){
            is ExerciseNameEvent.DeleteExerciseName -> {
                viewModelScope.launch {
                    dao.deleteExerciseName(event.exerciseName)
                }
            }
            ExerciseNameEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingExerciseName = false
                ) }
            }
            ExerciseNameEvent.SaveExerciseName -> {
                val title = state.value.title
                if(title.isBlank()){
                    return
                }
                val exerciseName = ExerciseName(title)
                viewModelScope.launch {
                    dao.insertExerciseName(exerciseName)
                }
                _state.update { it.copy(
                    isAddingExerciseName = false,
                    title = ""
                ) }
            }
            ExerciseNameEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingExerciseName = true
                ) }
            }
            is ExerciseNameEvent.SortExercisesNames -> {

            }
            is ExerciseNameEvent.setTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
        }
    }
}
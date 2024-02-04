package com.example.fitnessapplication.ExerciseNameDatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseNameViewModel(
    private val dao: ExerciseNameDao
): ViewModel(){

    private val _state = MutableStateFlow(ExerciseNameState())
    private val _clickedExerciseName = MutableStateFlow<String?>(null)
    val clickedExerciseName = _clickedExerciseName.asStateFlow()

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val exerciseNames = dao.getExerciseNames()
            _state.value = _state.value.copy(exerciseNames = exerciseNames)
        }
    }

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

            is ExerciseNameEvent.ClickedExerciseName -> {
                _clickedExerciseName.value = event.exerciseName.title
            }
        }
    }
}
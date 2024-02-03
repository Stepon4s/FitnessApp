package com.example.fitnessapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameEvent
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameState

@Composable
fun ExerciseNameScreen(
    state: ExerciseNameState,
    onEvent: (ExerciseNameEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ExerciseNameEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add exerciseName"
                )
            }
        },
        modifier = Modifier.padding(16.dp)
    ) { padding ->
        if (state.isAddingExerciseName) {
            AddExerciseNameDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.exerciseNames) { exerciseName ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .clickable { onEvent(ExerciseNameEvent.ClickedExerciseName(exerciseName)) }
                    ) {
                        Text(
                            text = "${exerciseName.title}",
                            fontSize = 20.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(ExerciseNameEvent.DeleteExerciseName(exerciseName))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete exerciseName"
                        )
                    }
                }
            }
        }
    }
}

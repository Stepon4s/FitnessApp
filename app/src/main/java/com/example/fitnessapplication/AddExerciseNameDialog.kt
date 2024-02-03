package com.example.fitnessapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseName
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameEvent
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameState


@Composable
fun AddExerciseNameDialog(
    state: ExerciseNameState,
    onEvent: (ExerciseNameEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onEvent(ExerciseNameEvent.HideDialog) },
        title = { Text(text = "Add Exercise Name") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = { onEvent(ExerciseNameEvent.setTitle(it)) },
                    placeholder = { Text(text = "Exercise Name") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val exerciseName = ExerciseName(state.title)
                    onEvent(ExerciseNameEvent.SaveExerciseName)
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                onClick = { onEvent(ExerciseNameEvent.HideDialog) }
            ) {
                Text(text = "Cancel")
            }
        },
        )
}









package com.example.fitnessapplication

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameEvent
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameState
import com.example.fitnessapplication.ui.theme.myFontFamily

@Composable
fun ExerciseNameScreen(
    state: ExerciseNameState,
    onEvent: (ExerciseNameEvent) -> Unit
) {
    val exerciseNames = state.exerciseNames.collectAsState(initial = emptyList())

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
            modifier = Modifier.fillMaxSize().background(color = Color(0xFF0D3D56)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exerciseNames.value) { exerciseName ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .clickable { onEvent(ExerciseNameEvent.ClickedExerciseName(exerciseName)) }
                    ) {
                        Text(
                            text = "${exerciseName.title}",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = myFontFamily
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

package com.example.fitnessapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameDatabase
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameViewModel

class ComposeExerciseListActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExerciseNameDatabase::class.java,
            "ExerciseName.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    private val viewModel by viewModels<ExerciseNameViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExerciseNameViewModel(db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_exercise_list)
        findViewById<ComposeView>(R.id.cvExerciseList).setContent {
            MaterialTheme {
                Surface {
                    val state by viewModel.state.collectAsState()
                    val clickedExerciseName by viewModel.clickedExerciseName.collectAsState()
                    ExerciseNameScreen(state = state, onEvent = viewModel::onEvent)
                    clickedExerciseName?.let {
                        val resultIntent = Intent().apply {
                            putExtra("selectedExercise", it)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                }
            }
        }
    }
}
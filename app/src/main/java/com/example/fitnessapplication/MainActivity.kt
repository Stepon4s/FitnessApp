package com.example.fitnessapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
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


class MainActivity : ComponentActivity() {

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
        setContentView(R.layout.activity_main)

        findViewById<ComposeView>(R.id.compose_view).setContent {
            MaterialTheme {
                Surface {
                    val state by viewModel.state.collectAsState()
                    ExerciseNameScreen(state = state, onEvent = viewModel::onEvent)
                }
            }
        }

//        val startButton = findViewById<Button>(R.id.button)
//        startButton.setOnClickListener {
//            val intent = Intent(this, WorkoutActivity::class.java)
//            startActivity(intent)
//        }
    }
}


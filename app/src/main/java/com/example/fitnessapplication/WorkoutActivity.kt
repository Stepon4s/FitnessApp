package com.example.fitnessapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.workoutDatabase.Exercise
import com.example.fitnessapplication.workoutDatabase.Set
import com.example.fitnessapplication.databinding.ActivityWorkoutBinding
import com.example.fitnessapplication.workoutDatabase.Workout
import com.example.fitnessapplication.workoutDatabase.WorkoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var rvEx: RecyclerView
    private lateinit var itemAdapter: ExerciseAdapter
    private lateinit var intent: ActivityResultLauncher<Intent>

    private val db by lazy {
        WorkoutDatabase.getDatabase(applicationContext)
    }
    private var workoutId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvEx = binding.rvExercises
        rvEx.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        intent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val exerciseTitle = result.data?.getStringExtra("selectedExercise")
                    if (exerciseTitle != null) {
                        val exercise = Exercise(exerciseTitle, workoutId)
                        var exerciseId = 0
                        GlobalScope.launch(Dispatchers.IO) {
                            exerciseId = db.workoutDao().insertExercise(exercise).toInt()
                            val set = Set(1, 0, 0.0, exerciseId)
                            db.workoutDao().insertSet(set)
                            refreshExercises()
                        }
                    }
                }
            }

        GlobalScope.launch(Dispatchers.IO) {
            if (db.workoutDao().getRowCount() != 0) {
                val saved = db.workoutDao().getLatestWorkout().saved
                workoutId = if (saved) {
                    val workout = Workout("Workout",startTime = System.currentTimeMillis(), endTime = 0, false)
                    db.workoutDao().insertWorkout(workout).toInt()
                } else {
                    db.workoutDao().getLatestWorkout().id
                }
            }
            else {
                val workout = Workout("Workout",startTime = System.currentTimeMillis(), endTime = 0, false)
                workoutId = db.workoutDao().insertWorkout(workout).toInt()
            }

            withContext(Dispatchers.Main) {
                itemAdapter = ExerciseAdapter(mutableListOf(), intent, workoutId)
                rvEx.adapter = itemAdapter
                refreshExercises()
            }
        }
    }

    private fun refreshExercises() {
        GlobalScope.launch(Dispatchers.IO) {
            val exercises = db.workoutDao().getExercisesForWorkout(workoutId)
            withContext(Dispatchers.Main) {
                itemAdapter.exercises = exercises.toMutableList()
                itemAdapter.notifyDataSetChanged()
            }
        }
    }
}
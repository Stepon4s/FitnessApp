package com.example.fitnessapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private var workoutId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var sets = ArrayList<Set>()
//        sets.add(Set(1, 10, 22.5))
//        var exerciseList = mutableListOf(
//            Exercise("Bench press", sets),
//        )

        GlobalScope.launch(Dispatchers.IO) {
            val workout = Workout(startTime = System.currentTimeMillis(), endTime = 0)
            workoutId = db.workoutDao().insertWorkout(workout).toInt()
        }

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

        itemAdapter = ExerciseAdapter(mutableListOf(), intent)

        rvEx = binding.rvExercises
        rvEx.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvEx.adapter = itemAdapter

        refreshExercises()
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
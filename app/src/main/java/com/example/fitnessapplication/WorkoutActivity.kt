package com.example.fitnessapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var rvEx: RecyclerView
    private var exerciseList = ArrayList<Exercise>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // temporary initiation
        var sets = ArrayList<Set>()
        sets.add(Set(1,10,22.5))
        var exerciseList = mutableListOf(
            Exercise("Bench press", sets),
            Exercise("Squat", sets)
        )

        val itemAdapter = ExerciseAdapter(exerciseList)
        rvEx = binding.rvExercises
        //rvEx.setHasFixedSize(true)
        rvEx.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )



        rvEx.adapter = itemAdapter

        binding.btnAddEx.setOnClickListener {
            val title = "Exercise"
            val exercise = Exercise(title, sets)
            exerciseList.add(exercise)
            itemAdapter.notifyItemInserted(exerciseList.size-1)
        }

    }
}
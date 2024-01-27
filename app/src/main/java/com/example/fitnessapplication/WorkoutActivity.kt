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
    lateinit var binding: ActivityWorkoutBinding
    lateinit var rvEx: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var exerciseList = mutableListOf(
            Exercise("Bench press"),
            Exercise("Squat")
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
            val exercise = Exercise(title)
            exerciseList.add(exercise)
            itemAdapter.notifyItemInserted(exerciseList.size-1)
        }

    }
}
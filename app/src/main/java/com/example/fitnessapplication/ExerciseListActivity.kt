package com.example.fitnessapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ActivityExerciseListBinding
import com.example.fitnessapplication.databinding.ExerciseForListTemplateBinding

class ExerciseListActivity : AppCompatActivity(), ExerciseListAdapter.OnItemClickListener {
    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var rvExList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var exerciseList = mutableListOf(
            "Bench press", "Deadlift", "Squat" )

        val itemAdapter = ExerciseListAdapter(exerciseList, this)
        rvExList = binding.rvExList
        rvExList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false )
        rvExList.adapter = itemAdapter

    }
    override fun onItemClick(exercise: String) {
        val intent = Intent()
        intent.putExtra("selectedExercise", exercise)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
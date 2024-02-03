package com.example.fitnessapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var rvEx: RecyclerView
    private lateinit var itemAdapter: ExerciseAdapter
    private lateinit var intent: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sets = ArrayList<Set>()
        sets.add(Set(1, 10, 22.5))
        var exerciseList = mutableListOf(
            Exercise("Bench press", sets),
        )

        intent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                    val exercise = result.data?.getStringExtra("selectedExercise")

                    val set = ArrayList<Set>()
                    set.add(Set(1, 10, 22.5))
                    exerciseList.add(Exercise(exercise as String, set))

                    itemAdapter.notifyDataSetChanged()
                }
            }

        itemAdapter = ExerciseAdapter(exerciseList, intent)

        rvEx = binding.rvExercises
        rvEx.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvEx.adapter = itemAdapter
    }


}
package com.example.fitnessapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseName
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameDao
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameDatabase
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameViewModel
import com.example.fitnessapplication.databinding.ActivityExerciseListBinding

class ExerciseListActivity : AppCompatActivity(), ExerciseListAdapter.OnItemClickListener {
    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var rvExList: RecyclerView
    private lateinit var exerciseNameDao: ExerciseNameDao
    private lateinit var exerciseNameViewModel : ExerciseNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseNameViewModel = ViewModelProvider(this).get(ExerciseNameViewModel::class.java)

        val database = Room.databaseBuilder(
            applicationContext,
            ExerciseNameDatabase::class.java, "exerciseName-database"
        ).build()
        exerciseNameDao = database.dao
        val exerciseNames = exerciseNameDao.getExerciseNames()

      //  val itemAdapter = ExerciseListAdapter(exerciseNames, this)
        rvExList = binding.rvExList
        rvExList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false )
        //rvExList.adapter = itemAdapter

    }
    override fun onItemClick(exercise: String) {
        val intent = Intent()
        intent.putExtra("selectedExercise", exercise)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    private fun showAddExerciseDialog(database: ExerciseNameDatabase) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_exercisename, null)

        builder.setView(view)
            .setTitle("Add Exercise")
            .setPositiveButton("Add") { _, _ ->
                // Handle positive button click
                val titleEditText = view.findViewById<EditText>(R.id.editTextTitle)
                val title = titleEditText.text.toString().trim()

                if (title.isNotEmpty()) {
                    // Insert the exercise into the database
                    val exercise = ExerciseName(title = title)
                    insertExerciseName(exercise)
                } else {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Handle negative button click (cancel)
                dialog.dismiss()
            }

        builder.create().show()
    }
    private fun insertExerciseName(exerciseName: ExerciseName){
        if (::exerciseNameViewModel.isInitialized) {
        //    exerciseNameViewModel.insertExerciseName(exerciseName)
        } else {
            return
        }
    }
}
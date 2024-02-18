package com.example.fitnessapplication

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.workoutDatabase.Exercise
import com.example.fitnessapplication.workoutDatabase.Set
import com.example.fitnessapplication.databinding.ExerciseTemplateBinding
import com.example.fitnessapplication.databinding.WorkoutFooterBinding
import com.example.fitnessapplication.databinding.WorkoutHeaderBinding
import com.example.fitnessapplication.workoutDatabase.WorkoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseAdapter(
var exercises: MutableList<Exercise>,
private var intent: ActivityResultLauncher<Intent>,
private val workoutId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        private const val TYPE_ITEM_HEADER = 0
        private const val TYPE_ITEM_FOOTER = 1
        private const val TYPE_ITEM_EXERCISE = 2
    }


    inner class HeaderViewHolder(private val binding: WorkoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){
                GlobalScope.launch(Dispatchers.IO) {
                    val db = WorkoutDatabase.getDatabase(binding.root.context)
                    val workout = db.workoutDao().getWorkoutById(workoutId)
                        workout.title = binding.nameWorkout.text.toString()
                        db.workoutDao().updateWorkout(workout)
                }
            }
        }

    inner class FooterViewHolder(private val binding: WorkoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnAddEx.setOnClickListener {
                intent.launch(Intent(binding.root.context, ComposeExerciseListActivity::class.java))
            }
            binding.btnSaveWorkout.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val db = WorkoutDatabase.getDatabase(binding.root.context)
                    val workout = db.workoutDao().getWorkoutById(workoutId)
                    workout.endTime = System.currentTimeMillis()
                    workout.saved = true
                    db.workoutDao().updateWorkout(workout)
                    (binding.root.context as WorkoutActivity).finish()
                }
            }
        }
    }

    inner class ExerciseViewHolder(private val binding: ExerciseTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {

            binding.exTitle.text = exercise.title
            val adapter = SetAdapter(mutableListOf())

            binding.rvSets.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rvSets.adapter = adapter

            val db by lazy {
                WorkoutDatabase.getDatabase(binding.root.context)
            }

            fun refreshSets() {
                GlobalScope.launch(Dispatchers.IO) {
                    val sets = db.workoutDao().getSetsForExercise(exercise.id)
                    withContext(Dispatchers.Main) {
                        adapter.sets = sets.toMutableList()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            refreshSets()

            binding.btnAddSet.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val setsCount = db.workoutDao().getSetsForExercise(exercise.id).size
                    val set = Set(setsCount + 1, weight = 0.0, reps = 0, exerciseId = exercise.id)
                    db.workoutDao().insertSet(set)
                    refreshSets()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM_HEADER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }

            TYPE_ITEM_FOOTER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutFooterBinding.inflate(layoutInflater, parent, false)
                return FooterViewHolder(binding)
            }

            TYPE_ITEM_EXERCISE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExerciseTemplateBinding.inflate(layoutInflater, parent, false)
                return ExerciseViewHolder(binding)
            }

            else -> {
                throw IllegalArgumentException("Invalid argument value (NO ITEM TYPE)")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExerciseViewHolder -> {
                val exercise = exercises[position - 1]
                holder.bind(exercise)
            }

            is FooterViewHolder -> {
                holder.bind()
            }

            is HeaderViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_ITEM_HEADER
        } else if (position == exercises.size + 1) {
            return TYPE_ITEM_FOOTER
        }
        return TYPE_ITEM_EXERCISE
    }
}
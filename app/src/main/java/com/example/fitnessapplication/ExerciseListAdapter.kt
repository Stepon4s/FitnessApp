package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseName
import com.example.fitnessapplication.databinding.ExerciseForListTemplateBinding



class ExerciseListAdapter(
    private var exercisesLiveData: LiveData<List<ExerciseName>>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ExerciseListAdapter.SetViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(exercise: String)
    }

    private var exercises: List<ExerciseName> = emptyList()

    init {
        exercisesLiveData.observeForever { updatedExercises ->
            exercises = updatedExercises
            notifyDataSetChanged()
        }
    }

    inner class SetViewHolder(private val binding: ExerciseForListTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseName) {
            binding.tvEx.text = exercise.title

            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(exercise.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ExerciseForListTemplateBinding.inflate(layoutInflater, parent, false)
        return SetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }
}
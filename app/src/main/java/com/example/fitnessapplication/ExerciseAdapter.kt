package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ExerciseTemplateBinding
import com.example.fitnessapplication.databinding.WorkoutFooterBinding
import com.example.fitnessapplication.databinding.WorkoutHeaderBinding

class ExerciseAdapter(
    private var exercises: MutableList<Exercise>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM_HEADER = 0
        private const val TYPE_ITEM_FOOTER = 1
        private const val TYPE_ITEM_EXERCISE = 2
    }

    inner class HeaderViewHolder(private val binding: WorkoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class FooterViewHolder(private val binding: WorkoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            // Set up button click listener
            binding.btnAddEx.setOnClickListener {
                val empty: MutableList<Set> = mutableListOf()
                empty.add(Set(1, 0, 0.0))
                val newExercise = Exercise("New Exercise", empty)
                exercises.add(newExercise)
                notifyItemInserted(exercises.size) // Notify adapter that an item is inserted
            }
        }
    }

    inner class ExerciseViewHolder(private val binding: ExerciseTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.exTitle.text = exercise.title
            val adapter = SetAdapter(exercise.sets)

            binding.rvSets.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rvSets.adapter = adapter

            binding.btnAddSet.setOnClickListener {
                exercise.sets.add(Set(exercise.sets.size + 1, 0, 0.0))
                adapter.notifyItemInserted(exercise.sets.size - 1)
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
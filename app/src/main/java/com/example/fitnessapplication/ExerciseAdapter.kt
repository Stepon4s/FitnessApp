package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ExerciseTemplateBinding
import com.example.fitnessapplication.databinding.WorkoutHeaderBinding

class ExerciseAdapter(
    private var exercises: List<Exercise>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER_NUMBER = 1
        private const val FOOTER_NUMBER = 1
        private const val TYPE_ITEM_HEADER = 0
        private const val TYPE_ITEM_FOOTER = 1
        private const val TYPE_ITEM = 1
    }
    inner class HeaderViewHolder(private val binding: WorkoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
    inner class ExerciseViewHolder(private val binding: ExerciseTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.exTitle.text = exercise.title
            var adapter = SetAdapter(exercise.sets)

            binding.rvSets.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rvSets.adapter = adapter

            binding.btnAddSet.setOnClickListener {
                exercise.sets.add(Set(exercise.sets.size + 1, 0, 0.0))
                adapter.notifyItemInserted(exercise.sets.size-1)
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
//
//            TYPE_ITEM_FOOTER -> {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = WorkoutFooterBinding.inflate(layoutInflater, parent, false)
//                return FooterViewHolder(binding)
//            }

            TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExerciseTemplateBinding.inflate(layoutInflater, parent, false)
                return ExerciseViewHolder(binding)
            }

            else -> { throw IllegalArgumentException("Invalid argument value (NO ITEM TYPE)")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExerciseViewHolder -> {
                val exercise = exercises[position]
                holder.bind(exercise)
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

//    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
//        val exercise = exercises[position]
//        holder.bind(exercise)
//    }

    override fun getItemViewType(position: Int): Int {
        if (0 != HEADER_NUMBER && position < HEADER_NUMBER) {
            return TYPE_ITEM_HEADER }
//         else if (0 != FOOTER_NUMBER && position >= (HEADER_NUMBER + exercises.size)) {
//            return TYPE_ITEM_FOOTER
//        }
        return TYPE_ITEM
    }
}
package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ExerciseForListTemplateBinding

class ExerciseListAdapter(
    private var exercises: List<String>,
    private val onItemClickListener : OnItemClickListener
) : RecyclerView.Adapter<ExerciseListAdapter.SetViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(exercise: String)
    }

    inner class SetViewHolder(private val binding: ExerciseForListTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: String) {
            binding.tvEx.text = exercise

            binding.root.setOnClickListener{
                onItemClickListener.onItemClick(exercise)
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



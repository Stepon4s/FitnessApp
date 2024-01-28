package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.databinding.ExerciseTemplateBinding
import com.example.fitnessapplication.databinding.SetTemplateBinding

class SetAdapter(
    private var sets : List<Set>
) : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    inner class SetViewHolder(private val binding: SetTemplateBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(set: Set){
                    binding.tvNumber.text = set.number.toString()
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SetTemplateBinding.inflate(layoutInflater, parent, false)
        return SetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val set = sets[position]
        holder.bind(set)
    }
}
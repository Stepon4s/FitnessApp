package com.example.fitnessapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.workoutDatabase.Set
import com.example.fitnessapplication.databinding.SetTemplateBinding
import com.example.fitnessapplication.workoutDatabase.WorkoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetAdapter(
    var sets : MutableList<Set>
) : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    inner class SetViewHolder(private val binding: SetTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(set: Set){
            val db by lazy {
                WorkoutDatabase.getDatabase(binding.root.context)
            }
            binding.tvNumber.text = set.number.toString()
            val weightText = if (set.weight % 1 == 0.0) {
                set.weight.toInt().toString()
            } else {
                set.weight.toString()
            }
            binding.editTextWeight.setText(weightText)
            binding.editTextReps.setText(set.reps.toString())

            val weightWatcher = binding.editTextWeight.doAfterTextChanged { text ->
                GlobalScope.launch(Dispatchers.IO) {
                    set.weight = text.toString().toDoubleOrNull() ?: 0.0
                    db.workoutDao().updateSet(set)
                }
            }
            val repsWatcher = binding.editTextReps.doAfterTextChanged { text ->
                GlobalScope.launch(Dispatchers.IO) {
                    set.reps = text.toString().toIntOrNull() ?: 0
                    db.workoutDao().updateSet(set)
                }
            }

            // Remove the text watchers when the view is recycled
            binding.root.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {}
                override fun onViewDetachedFromWindow(v: View) {
                    binding.editTextWeight.removeTextChangedListener(weightWatcher)
                    binding.editTextReps.removeTextChangedListener(repsWatcher)
                }
            })
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
package com.example.fitnessapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.example.fitnessapplication.workoutDatabase.Exercise
import com.example.fitnessapplication.workoutDatabase.Set
import com.example.fitnessapplication.workoutDatabase.Workout
import com.example.fitnessapplication.workoutDatabase.WorkoutDao
import com.example.fitnessapplication.workoutDatabase.WorkoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private lateinit var db: WorkoutDatabase
    private lateinit var workoutDao: WorkoutDao


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = WorkoutDatabase.getDatabase(requireContext())
        workoutDao = db.workoutDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val composeView = ComposeView(requireContext())

        // Set the content of the ComposeView
        composeView.setContent {
            val coroutineScope = rememberCoroutineScope()
            var workouts by remember { mutableStateOf(listOf<Workout>()) }

            MaterialTheme {
                Surface(color = Color(0xFF0D3D56)) {
                    if (workouts.isNotEmpty()) {
                        RecyclerView(workouts, workoutDao)
                    } else {
                        // Show a loading indicator while the workouts are being loaded
                        CircularProgressIndicator()
                    }
                }
            }

            // Load the workouts in the background
            coroutineScope.launch {
                workouts = workoutDao.getAllWorkouts()
            }
        }

        // Return the ComposeView
        return composeView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
@Composable
fun ListItem(workout: Workout, workoutDao: WorkoutDao) {

    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )
    val coroutineScope = rememberCoroutineScope()
    val exercises = remember { mutableStateOf(listOf<Exercise>()) }

    Surface(
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "${workout.id}. ${workout.title} ")
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    Text(text = "Date: ${format.format(Date(workout.startTime))}")
                    if(!workout.saved) {
                        Text(text = "Duration: Not finished")
                    } else {
                        Text(text = "Duration: ${getDuration(workout.startTime, workout.endTime)}")
                    }
                }
                OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                    Text(if (expanded.value) "Show less" else "Show more")
                }
            }
            if (expanded.value) {
                coroutineScope.launch(Dispatchers.IO) {
                    val fetchedExercises = workoutDao.getExercisesForWorkout(workout.id)
                    withContext(Dispatchers.Main) {
                        exercises.value = fetchedExercises
                    }
                }
                Column(
                    modifier = Modifier.padding(
                        //top = 8.dp
                    )
                ) {
                    exercises.value.forEach { exercise ->
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = "${exercise.title}:")
                        val sets = remember { mutableStateOf(listOf<Set>()) }
                        coroutineScope.launch(Dispatchers.IO) {
                            val fetchedSets = workoutDao.getSetsForExercise(exercise.id)
                            withContext(Dispatchers.Main) {
                                sets.value = fetchedSets
                            }
                        }
                        sets.value.forEach { set ->
                            Text(text = "${set.reps} x ${set.weight}kg")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecyclerView(workouts: List<Workout>, workoutDao: WorkoutDao) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        items(items = workouts.reversed()) { workout ->
            ListItem(workout, workoutDao)
        }
    }

}

fun getDuration(startTime: Long, endTime: Long): String {
    val durationMillis = endTime - startTime + 30 * 1000

    val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) - TimeUnit.HOURS.toMinutes(hours)

    return String.format("%02d:%02d", hours, minutes)
}
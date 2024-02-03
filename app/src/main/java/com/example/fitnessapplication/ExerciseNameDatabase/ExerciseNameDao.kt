package com.example.fitnessapplication.ExerciseNameDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseNameDao {
        @Insert
        suspend fun insertExerciseName(exerciseName: ExerciseName)

        @Delete
        suspend fun deleteExerciseName(exerciseName: ExerciseName)

        @Query("SELECT * FROM exerciseName ORDER BY title ASC")
        fun getExerciseNames() : Flow<List<ExerciseName>>

}
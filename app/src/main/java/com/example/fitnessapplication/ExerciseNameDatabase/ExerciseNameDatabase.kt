package com.example.fitnessapplication.ExerciseNameDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ExerciseName::class],
    version = 2
)
abstract class ExerciseNameDatabase : RoomDatabase() {

    abstract val dao: ExerciseNameDao

    companion object {
        private var INSTANCE: ExerciseNameDatabase? = null

        fun getInstance(context: Context): ExerciseNameDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseNameDatabase::class.java,
                    "exerciseName-database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
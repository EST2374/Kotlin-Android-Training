package com.example.training4.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.training4.data.habit.Habit

@Database(
    entities = [Habit::class],
    version = 2
)
@TypeConverters(DateConverters::class)
abstract class HabitDatabase: RoomDatabase() {

    abstract val dao: HabitDao

}
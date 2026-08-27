package com.example.training4.data.habit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.training4.data.repository.DateConverters

@Entity
@TypeConverters(DateConverters::class)
data class Habit(
    val title: String,
    val description: String,
    val category: HabitCategory = HabitCategory.HEALTH,
    val frequency: Frequency = Frequency.DAILY,
    val currentStreak: Int = 0,
    var isCompletedToday: Boolean = false,
    val completedDates: List<String> = emptyList(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

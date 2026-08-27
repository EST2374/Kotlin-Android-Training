package com.example.training4.data.repository

import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory

data class HabitState(
    val habits: List<Habit> = emptyList(),
    val title: String = "",
    val description: String = "",
    val category: HabitCategory = HabitCategory.HEALTH,
    val frequency: Frequency = Frequency.DAILY,
    val currentStreak: Int = 0,
    var isCompletedToday: Boolean = false,
    val isAddingNewHabit: Boolean = false,
    val sortType: SortType = SortType.TITLE
)
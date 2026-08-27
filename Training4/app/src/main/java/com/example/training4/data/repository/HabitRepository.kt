package com.example.training4.data.repository

import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getHabits(sortType: SortType = SortType.TITLE): Flow<List<Habit>>

    suspend fun getHabitById(id: Int): Habit

    suspend fun addHabit(
        title: String,
        description: String,
        category: HabitCategory,
        frequency: Frequency
    )

    suspend fun toggleCompleteToday(id: Int)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(id: Int)
}
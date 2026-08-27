package com.example.training4.data.repository

import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HabitRepositoryImpl(
    private val dao: HabitDao
) : HabitRepository {

    override fun getHabits(sortType: SortType): Flow<List<Habit>> {
        return when (sortType) {
            SortType.TITLE -> dao.getHabitByTitle()
            SortType.CATEGORY -> dao.getHabitByCategory()
            SortType.FREQUENCY -> dao.getHabitByFrequency()
        }
    }

    override suspend fun getHabitById(id: Int): Habit {
        return dao.getHabitById(id) ?: throw Exception("Habit not found")
    }

    override suspend fun addHabit(
        title: String,
        description: String,
        category: HabitCategory,
        frequency: Frequency
    ) {
        val newHabit = Habit(
            title = title,
            description = description,
            category = category,
            frequency = frequency,
            currentStreak = 0,
            isCompletedToday = false,
        )
        dao.upsertHabit(newHabit)
    }

    override suspend fun toggleCompleteToday(id: Int) {
        val habit = dao.getHabitById(id)
        if (habit != null) {
            val today = LocalDate.now()
            val todayStr = today.format(DateTimeFormatter.ISO_LOCAL_DATE)
            
            val isCurrentlyCompleted = habit.completedDates.contains(todayStr)
            val newCompletedDates = if (isCurrentlyCompleted) {
                habit.completedDates.filter { it != todayStr }
            } else {
                habit.completedDates + todayStr
            }

            // Calculate streak
            val sortedDates = newCompletedDates
                .map { LocalDate.parse(it) }
                .sortedDescending()
            
            var streak = 0
            var checkDate = if (newCompletedDates.contains(todayStr)) today else today.minusDays(1)
            
            for (date in sortedDates) {
                if (date == checkDate) {
                    streak++
                    checkDate = checkDate.minusDays(1)
                } else if (date.isBefore(checkDate)) {
                    break
                }
            }

            val updatedHabit = habit.copy(
                isCompletedToday = !isCurrentlyCompleted,
                completedDates = newCompletedDates,
                currentStreak = streak
            )
            dao.upsertHabit(updatedHabit)
        }
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.upsertHabit(habit)
    }

    override suspend fun deleteHabit(id: Int) {
        val habit = dao.getHabitById(id)
        if (habit != null) {
            dao.deleteHabit(habit)
        }
    }
}

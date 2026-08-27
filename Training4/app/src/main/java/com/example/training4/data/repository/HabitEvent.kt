package com.example.training4.data.repository

import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory

sealed interface HabitEvent {

    object SaveHabit: HabitEvent

    data class SetHabitTitle(val title: String): HabitEvent

    data class SetHabitDescription(val description: String): HabitEvent

    data class SetHabitCategory(val category: HabitCategory): HabitEvent

    data class SetHabitFrequency(val frequency: Frequency): HabitEvent


    object ShowDialog: HabitEvent

    object HideDialog: HabitEvent

    data class SortHabits(val sortType: SortType): HabitEvent

    data class DeleteHabit(val habit: Habit): HabitEvent

}
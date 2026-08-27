package com.example.training4.navigation

import com.example.training4.data.habit.Habit
import kotlinx.serialization.Serializable

@Serializable
sealed class HabitDestinations {
    @Serializable
    data object HabitList : HabitDestinations()

    @Serializable
    data class HabitDetail(val habitId: Int) : HabitDestinations()

    @Serializable
    data object Settings : HabitDestinations()

    @Serializable
    data object Statistics : HabitDestinations()
}

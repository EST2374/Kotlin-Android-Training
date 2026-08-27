package com.example.training4.ui.state

import com.example.training4.data.habit.Habit

sealed interface HabitUiState {

    object Loading: HabitUiState

    data class Success(
        val habits: List<Habit>
    ): HabitUiState

    data class Error(val message: String): HabitUiState
}
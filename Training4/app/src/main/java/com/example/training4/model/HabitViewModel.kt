package com.example.training4.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import com.example.training4.data.repository.HabitRepository
import com.example.training4.data.repository.HabitRepositoryImpl
import com.example.training4.data.repository.SortType
import com.example.training4.ui.state.HabitUiState
import com.example.training4.ui.state.HabitUiState.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HabitUiState>(HabitUiState.Loading)
    val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.TITLE)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()

    init {
        observeHabits()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeHabits() {
        viewModelScope.launch {
            _sortType.flatMapLatest { sortType ->
                habitRepository.getHabits(sortType)
            }.collect { habitsList ->
                _uiState.value = Success(habitsList)
            }
        }
    }

    fun onSortTypeChange(sortType: SortType) {
        _sortType.value = sortType
    }

    fun onToggleComplete(id: Int) {
        viewModelScope.launch {
            habitRepository.toggleCompleteToday(id)
        }
    }

    fun addHabit(
        title: String,
        description: String,
        category: HabitCategory,
        frequency: Frequency
    ) {
        viewModelScope.launch {
            habitRepository.addHabit(
                title,
                description,
                category,
                frequency
            )
        }
    }

    fun deleteHabit(
        habit: Habit
    ) {
        viewModelScope.launch {
            habitRepository.deleteHabit(habit.id)
        }
    }

    fun updateHabit(
        habit: Habit
    ) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit)
        }
    }

}
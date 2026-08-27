package com.example.training4.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.savedState
import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import com.example.training4.data.repository.HabitDao
import com.example.training4.data.repository.HabitEvent
import com.example.training4.data.repository.HabitState
import com.example.training4.data.repository.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitDaoViewModel(
    private val dao: HabitDao
): ViewModel() {

    private val _state = MutableStateFlow(HabitState())

    private val _sortType = MutableStateFlow(SortType.TITLE)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _habits = _sortType
        .flatMapLatest { sortType ->
                when(sortType) {
                    SortType.TITLE -> dao.getHabitByTitle()
                    SortType.CATEGORY -> dao.getHabitByCategory()
                    SortType.FREQUENCY -> dao.getHabitByFrequency()
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(),
        emptyList()
                )
        }

    val state = combine(_state,_sortType,_habits) { state, sortType, habits ->
        state.copy(
            habits = habits,
            sortType = sortType
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HabitState()
    )


    fun onEvent(event: HabitEvent) {
        when(event) {
            is HabitEvent.DeleteHabit -> {
                viewModelScope.launch {
                    dao.deleteHabit(event.habit)
                }
            }
            HabitEvent.HideDialog -> {
                _state.update { it.copy(
                        isAddingNewHabit = false
                    )
                }
            }
            HabitEvent.SaveHabit -> {
                val title = state.value.title
                val description = state.value.description
                val category = state.value.category
                val frequency = state.value.frequency
                val currentStreak = state.value.currentStreak
                val isCompleted = state.value.isCompletedToday


                if (title.isBlank() || description.isBlank() || category.name.isBlank() || frequency.name.isBlank() ||
                    currentStreak.toString().isBlank() || isCompleted.toString().isBlank()) {
                    return
                }

                val newHabit = Habit(
                    title = title,
                    description = description,
                    category = category,
                    frequency = frequency,
                )

                viewModelScope.launch {
                    dao.upsertHabit(newHabit)
                }
                _state.update { it.copy(
                    title = "",
                    description = "",
                    category = HabitCategory.HEALTH,
                    frequency = Frequency.DAILY,
                ) }

            }
            is HabitEvent.SetHabitCategory -> {
                _state.update { it.copy(
                    category = event.category
                ) }
            }
            is HabitEvent.SetHabitDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is HabitEvent.SetHabitFrequency -> {
                _state.update { it.copy(
                    frequency = event.frequency
                ) }
            }
            is HabitEvent.SetHabitTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            HabitEvent.ShowDialog -> {
                _state.update { it.copy(
                        isAddingNewHabit = true
                    )
                }
            }
            is HabitEvent.SortHabits -> {
                _sortType.value = event.sortType
            }
        }
    }


}
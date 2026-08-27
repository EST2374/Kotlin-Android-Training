package com.example.training4.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.training4.data.habit.Frequency
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import com.example.training4.data.repository.HabitEvent
import com.example.training4.data.repository.HabitState
import com.example.training4.data.repository.SortType
import com.example.training4.model.HabitViewModel
import com.example.training4.screens.components.HabitCard
import com.example.training4.ui.state.HabitUiState

@Composable
fun HabitListScreenDao(
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    ) {

    // val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var editingHabit by remember { mutableStateOf<Habit?>(null) }
    val titleState = rememberTextFieldState()
    val descriptionState = rememberTextFieldState()
    var selectedCategory by remember { mutableStateOf(HabitCategory.HEALTH) }
    var selectedFrequency by remember { mutableStateOf(Frequency.DAILY) }
    var isAddingHabit by remember { mutableStateOf(false) }

    fun startEditing(habit: Habit) {
        editingHabit = habit
        titleState.edit { replace(0, length, habit.title) }
        descriptionState.edit { replace(0, length, habit.description) }
        selectedCategory = habit.category
        selectedFrequency = habit.frequency
        isAddingHabit = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(HabitEvent.ShowDialog)
                }
            ) {
                Icon(Icons.Default.Add,"Add Button")
            }
        }
    ) { innerPadding ->

        if(state.isAddingNewHabit) {
            AddEditHabitScreen(
                state = state,
                onEvent = onEvent
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HabitFlow",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.Settings, contentDescription = "Settings Button")
            }

            Spacer(modifier = Modifier.padding(4.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SortType.entries.forEach { sortType ->
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onEvent(HabitEvent.SortHabits(sortType))
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.sortType == sortType,
                                    onClick = {
                                        onEvent(HabitEvent.SortHabits(sortType))
                                    }
                                )
                                Text(sortType.name)
                            }
                        }

                    }
                }
            }

            /*
            // Main Content Body based on UI State
            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    is HabitUiState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Text("Loading Data", modifier = Modifier.padding(top = 8.dp))
                        }
                    }

                    is HabitUiState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    is HabitUiState.Success -> {
                        if (state.habits.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No habits yet. Tap + to add one!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            val habitsAllCount = state.habits.size
                            val habitsCompletedTodayCount = state.habits.count { it.isCompletedToday }

                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "$habitsCompletedTodayCount out of $habitsAllCount habits completed today",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(bottom = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(state.habits, key = { it.id }) { habit ->
                                        HabitCard(
                                            habit = habit,
                                            onEditClick = { startEditing(habit = habit) },
                                            onDeleteClick = { viewModel.deleteHabit(habit) },
                                            onDoneClick = { viewModel.onToggleComplete(habit.id) },
                                            onHabitClick = onHabitClick
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

             */
        }
    }
}

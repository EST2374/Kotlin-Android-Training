package com.example.training4.navigation

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.training4.model.HabitViewModel
import com.example.training4.screens.HabitDetailScreen
import com.example.training4.screens.HabitListScreen
import com.example.training4.screens.SettingsScreen
import com.example.training4.screens.StatisticsScreen
import com.example.training4.ui.state.HabitUiState

@Composable
fun HabitNavDisplay(
    viewModel: HabitViewModel
) {
    val backStack = remember { mutableStateListOf<HabitDestinations>(HabitDestinations.HabitList) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentDestination = backStack.lastOrNull() ?: HabitDestinations.HabitList

    Scaffold(
        bottomBar = {
            if (currentDestination is HabitDestinations.HabitList || 
                currentDestination is HabitDestinations.Statistics || 
                currentDestination is HabitDestinations.Settings) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination is HabitDestinations.HabitList,
                        onClick = { 
                            if (currentDestination !is HabitDestinations.HabitList) {
                                backStack.clear()
                                backStack.add(HabitDestinations.HabitList)
                            }
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Habits") }
                    )
                    NavigationBarItem(
                        selected = currentDestination is HabitDestinations.Statistics,
                        onClick = { 
                            if (currentDestination !is HabitDestinations.Statistics) {
                                backStack.clear()
                                backStack.add(HabitDestinations.Statistics)
                            }
                        },
                        icon = { Icon(Icons.Default.Assessment, contentDescription = null) },
                        label = { Text("Stats") }
                    )
                    NavigationBarItem(
                        selected = currentDestination is HabitDestinations.Settings,
                        onClick = { 
                            if (currentDestination !is HabitDestinations.Settings) {
                                backStack.clear()
                                backStack.add(HabitDestinations.Settings)
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Settings") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.padding(innerPadding)
        ) { key ->
            when (key) {
                HabitDestinations.HabitList -> {
                    NavEntry(key) {
                        HabitListScreen(
                            viewModel = viewModel,
                            onHabitClick = { habit ->
                                backStack.add(HabitDestinations.HabitDetail(habit.id))
                            }
                        )
                    }
                }
                is HabitDestinations.HabitDetail -> {
                    NavEntry(key) {
                        val state = uiState
                        if (state is HabitUiState.Success) {
                            val habit = state.habits.find { it.id == key.habitId }
                            if (habit != null) {
                                HabitDetailScreen(
                                    habit = habit,
                                    viewModel = viewModel,
                                    onBack = { backStack.removeLastOrNull() },
                                    onEdit = { /* Implementation for edit if needed */ }
                                )
                            } else {
                                backStack.removeLastOrNull()
                            }
                        }
                    }
                }
                HabitDestinations.Settings -> {
                    NavEntry(key) {
                        SettingsScreen(onBack = { 
                            backStack.clear()
                            backStack.add(HabitDestinations.HabitList)
                        })
                    }
                }
                HabitDestinations.Statistics -> {
                    NavEntry(key) {
                        StatisticsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

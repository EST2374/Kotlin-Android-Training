package com.example.training4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.training4.model.HabitViewModel
import com.example.training4.ui.state.HabitUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(viewModel: HabitViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Performance") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState is HabitUiState.Success) {
                val habits = (uiState as HabitUiState.Success).habits
                val totalHabits = habits.size
                val completedToday = habits.count { it.isCompletedToday }
                val bestStreak = habits.maxOfOrNull { it.currentStreak } ?: 0

                StatCard("Completion Rate", "${if(totalHabits > 0) (completedToday * 100 / totalHabits) else 0}%")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("Active Habits", totalHabits.toString(), Modifier.weight(1f))
                    StatCard("Best Streak", "$bestStreak Days", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
    }
}

package com.example.training4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.training4.data.habit.Habit
import com.example.training4.model.HabitViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    habit: Habit,
    viewModel: HabitViewModel,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        viewModel.deleteHabit(habit)
                        onBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title and Description
            Column {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                if (habit.description.isNotEmpty()) {
                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Stats Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Streak", style = MaterialTheme.typography.labelMedium)
                        Text(
                            "${habit.currentStreak} Days",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                OutlinedCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Category", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(8.dp))
                        AssistChip(
                            onClick = { },
                            label = { Text(habit.category.name) }
                        )
                        Text(
                            habit.frequency.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Text(
                "Activity History",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Last 7 Days (Visual representation)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val today = LocalDate.now()
                val lastSevenDays = (0..6).map { today.minusDays(it.toLong()) }.reversed()
                val locale = LocalConfiguration.current.locales[0]
                
                lastSevenDays.forEach { date ->
                    val isDone = habit.completedDates.contains(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale).take(2)
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDone) 
                                    MaterialTheme.colorScheme.primaryContainer 
                                else 
                                    MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(
                                        Icons.Default.Done,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                        Text(
                            text = dayName,
                            modifier = Modifier.padding(top = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (date == today) MaterialTheme.colorScheme.primary else Color.Unspecified,
                            fontWeight = if (date == today) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { viewModel.onToggleComplete(habit.id) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !habit.isCompletedToday
            ) {
                Text(if (habit.isCompletedToday) "Completed for Today" else "Mark as Done")
            }
        }
    }
}

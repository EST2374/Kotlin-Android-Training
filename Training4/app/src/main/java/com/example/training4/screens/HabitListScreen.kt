package com.example.training4.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.training4.data.repository.SortType
import com.example.training4.model.HabitViewModel
import com.example.training4.screens.components.CategoryButtons
import com.example.training4.screens.components.FrequencyButtons
import com.example.training4.screens.components.HabitCard
import com.example.training4.ui.state.HabitUiState

@Composable
fun HabitListScreen(
    viewModel: HabitViewModel,
    modifier: Modifier = Modifier,
    onHabitClick: (Habit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sortType by viewModel.sortType.collectAsStateWithLifecycle()
    var editingHabit by remember { mutableStateOf<Habit?>(null) }
    val titleState = rememberTextFieldState()
    val descriptionState = rememberTextFieldState()
    var selectedCategory by remember { mutableStateOf(HabitCategory.HEALTH) }
    var selectedFrequency by remember { mutableStateOf(Frequency.DAILY) }
    var isAddingHabit by remember { mutableStateOf(false) }

    fun resetForm() {
        titleState.clearText()
        descriptionState.clearText()
        selectedCategory = HabitCategory.HEALTH
        selectedFrequency = Frequency.DAILY
        isAddingHabit = false
        editingHabit = null
    }

    fun startEditing(habit: Habit) {
        editingHabit = habit
        titleState.edit { replace(0, length, habit.title) }
        descriptionState.edit { replace(0, length, habit.description) }
        selectedCategory = habit.category
        selectedFrequency = habit.frequency
        isAddingHabit = true
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (!isAddingHabit) {
                ExtendedFloatingActionButton(
                    onClick = { isAddingHabit = true },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add Habit Button") },
                    text = { Text("Add Habit") }
                )
            }
        }
    ) { innerPadding ->
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
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            // Sort Options
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortType.entries.forEach { type ->
                    Row(
                        modifier = Modifier.clickable { viewModel.onSortTypeChange(type) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = sortType == type,
                            onClick = { viewModel.onSortTypeChange(type) }
                        )
                        Text(
                            text = type.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

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

            // Input Form Card
            AnimatedVisibility(visible = isAddingHabit) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (editingHabit != null) "Edit Habit" else "New Habit",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { resetForm() }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }

                        TextField(
                            state = titleState,
                            placeholder = { Text("Title") },
                            modifier = Modifier.fillMaxWidth(),
                            lineLimits = TextFieldLineLimits.SingleLine
                        )
                        TextField(
                            state = descriptionState,
                            placeholder = { Text("Description") },
                            modifier = Modifier.fillMaxWidth(),
                            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 2)
                        )

                        Text("Category", style = MaterialTheme.typography.labelLarge)
                        CategoryButtons(
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Frequency", style = MaterialTheme.typography.labelLarge)
                        FrequencyButtons(
                            selectedFrequency = selectedFrequency,
                            onFrequencySelected = { selectedFrequency = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (editingHabit != null) {
                                    viewModel.updateHabit(
                                        editingHabit!!.copy(
                                            title = titleState.text.toString(),
                                            description = descriptionState.text.toString(),
                                            category = selectedCategory,
                                            frequency = selectedFrequency
                                        )
                                    )
                                } else {
                                    viewModel.addHabit(
                                        title = titleState.text.toString(),
                                        description = descriptionState.text.toString(),
                                        category = selectedCategory,
                                        frequency = selectedFrequency
                                    )
                                }
                                resetForm()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = titleState.text.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = if (editingHabit != null) Icons.Default.Done else Icons.Default.Add,
                                contentDescription = null
                            )
                            Spacer(Modifier.padding(4.dp))
                            Text(if (editingHabit != null) "Update Habit" else "Create Habit")
                        }
                    }
                }
            }
        }
    }
}

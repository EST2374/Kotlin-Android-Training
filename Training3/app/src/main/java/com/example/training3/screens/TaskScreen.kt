package com.example.training3.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.training3.data.task.Category
import com.example.training3.data.task.Priority
import com.example.training3.data.task.Task
import com.example.training3.model.TaskViewModel
import com.example.training3.screens.components.CategoryButtons
import com.example.training3.screens.components.PriorityButtons
import com.example.training3.screens.components.TaskFilterButtons
import com.example.training3.screens.components.TaskItem
import com.example.training3.screens.components.TasksSearchBar
import com.example.training3.ui.state.TaskUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val titleState = rememberTextFieldState()
    val contentState = rememberTextFieldState()

    var selectedPriority by remember { mutableStateOf(Priority.LOW) }
    var selectedCategory by remember { mutableStateOf(Category.PERSONAL) }
    var isAddingTask by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    fun resetForm() {
        titleState.clearText()
        contentState.clearText()
        selectedPriority = Priority.LOW
        selectedCategory = Category.PERSONAL
        isAddingTask = false
        editingTask = null
    }

    fun startEditing(task: Task) {
        editingTask = task
        titleState.edit { replace(0, length, task.title) }
        contentState.edit { replace(0, length, task.content) }
        selectedPriority = task.priority
        selectedCategory = task.category
        isAddingTask = true
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (!isAddingTask) {
                ExtendedFloatingActionButton(
                    onClick = { 
                        resetForm()
                        isAddingTask = true 
                    },
                    icon = { Icon(Icons.Default.Add, null) },
                    text = { Text("Add Task") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Header Section
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tasks",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                
                val taskCount = (uiState as? TaskUiState.Success)?.tasks?.size ?: 0
                Text(
                    text = "$taskCount tasks available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                TasksSearchBar(
                    query = (uiState as? TaskUiState.Success)?.searchQuery ?: "",
                    onQueryChange = { viewModel.onSearchQueryChanged(it) }
                )

                TaskFilterButtons(
                    activeFilter = (uiState as? TaskUiState.Success)?.activeFilter ?: com.example.training3.data.task.TaskFilter.ALL,
                    onFilterSelected = { viewModel.onFilterSelected(it) }
                )
            }

            // Task List Section
            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    is TaskUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    TaskUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is TaskUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                            items(state.tasks, key = { it.id }) { task ->
                                TaskItem(
                                    task = task,
                                    onFavoriteClick = { viewModel.toggleFavorite(task.id) },
                                    onEditClick = { startEditing(task) },
                                    onDeleteClick = { viewModel.deleteTask(task.id) }
                                )
                            }
                        }
                    }
                }
            }

            // Inline Add/Edit Task Form (Animated)
            AnimatedVisibility(visible = isAddingTask) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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
                                text = if (editingTask != null) "Edit Task" else "New Task",
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
                            state = contentState,
                            placeholder = { Text("Content") },
                            modifier = Modifier.fillMaxWidth(),
                            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 2)
                        )

                        Text("Category", style = MaterialTheme.typography.labelLarge)
                        CategoryButtons(
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Priority", style = MaterialTheme.typography.labelLarge)
                        PriorityButtons(
                            selectedPriority = selectedPriority,
                            onPrioritySelected = { selectedPriority = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (editingTask != null) {
                                    viewModel.updateTask(
                                        editingTask!!.copy(
                                            title = titleState.text.toString(),
                                            content = contentState.text.toString(),
                                            category = selectedCategory,
                                            priority = selectedPriority
                                        )
                                    )
                                } else {
                                    viewModel.addTask(
                                        title = titleState.text.toString(),
                                        content = contentState.text.toString(),
                                        category = selectedCategory,
                                        priority = selectedPriority
                                    )
                                }
                                resetForm()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(if (editingTask != null) Icons.Default.Done else Icons.Default.Add, null)
                            Spacer(Modifier.padding(4.dp))
                            Text(if (editingTask != null) "Update Task" else "Create Task")
                        }
                    }
                }
            }
        }
    }
}

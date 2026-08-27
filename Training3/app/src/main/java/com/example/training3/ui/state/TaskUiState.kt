package com.example.training3.ui.state

import com.example.training3.data.task.Task
import com.example.training3.data.task.TaskFilter

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Success(
        val tasks: List<Task>,
        val activeFilter: TaskFilter = TaskFilter.ALL,
        val searchQuery: String = ""
    ) : TaskUiState
    data class Error(val message: String) : TaskUiState
}
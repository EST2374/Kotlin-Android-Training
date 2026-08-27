package com.example.training3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training3.data.repository.TaskRepository
import com.example.training3.data.repository.TaskRepositoryImpl
import com.example.training3.data.task.Category
import com.example.training3.data.task.Priority
import com.example.training3.data.task.Task
import com.example.training3.data.task.TaskFilter
import com.example.training3.ui.state.TaskUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository = TaskRepositoryImpl(),
) : ViewModel()
{

    private val _currentFilter = MutableStateFlow(TaskFilter.ALL)
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<TaskUiState> = combine(
        taskRepository.getTask(),
        _currentFilter,
        _searchQuery
    ) { allTasks, filter, query ->

        val filteredTasks = allTasks.filter { task ->

            // 1. Kriterium: Passt der Filter?
            val matchesFilter = when (filter) {
                TaskFilter.ALL -> true
                TaskFilter.WORK -> task.category == Category.WORK
                TaskFilter.PERSONAL -> task.category == Category.PERSONAL
                TaskFilter.URGENT -> task.category == Category.URGENT
                TaskFilter.FAVORITES_ONLY -> task.isFavorite
            }

            // 2. Kriterium: Passt die Suchanfrage? (Falls query leer ist, wird alles durchgewunken)
            val matchesSearch = query.isBlank() ||
                    task.title.contains(query, ignoreCase = true) ||
                    task.content.contains(query, ignoreCase = true)

            // Nur wenn BEIDE Kriterien wahr sind, bleibt die Task in der Liste
            matchesFilter && matchesSearch
        }

        TaskUiState.Success(
            tasks = filteredTasks,
            activeFilter = filter,
            searchQuery = query
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskUiState.Loading
    )

    fun onFilterSelected(filter: TaskFilter) {
        _currentFilter.value = filter
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun addTask(title: String, content: String, category: Category, priority: Priority) {
        viewModelScope.launch {
            taskRepository.addTask(title,content,category,priority)
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            taskRepository.toggleFavorite(id)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            taskRepository.deleteTaskById(id)
        }
    }
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun getTask(): Flow<List<Task>> {
        return taskRepository.getTask()
    }
}
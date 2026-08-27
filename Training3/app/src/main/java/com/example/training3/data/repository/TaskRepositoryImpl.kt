package com.example.training3.data.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import com.example.training3.data.task.Category
import com.example.training3.data.task.Priority
import com.example.training3.data.task.Task
import kotlinx.coroutines.flow.Flow


class TaskRepositoryImpl: TaskRepository {

    private val tasks = mutableStateListOf<Task>()
    private var nextId = 0

    override fun getTask(): Flow<List<Task>> {
        return snapshotFlow {  tasks.toList() }
    }

    override suspend fun addTask(
        title: String,
        content: String,
        category: Category,
        priority: Priority
    ) {
        val newTask = Task(
            id = nextId,
            title = title,
            content = content,
            category = category,
            priority = priority,
            isFavorite = false
        )
        tasks.add(newTask)
        nextId++

    }

    override suspend fun toggleFavorite(id: Int) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) {
            val task = tasks[index]
            tasks[index] = task.copy(isFavorite = !task.isFavorite)
        }
    }

    override suspend fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
    }

    override suspend fun deleteTaskById(id: Int) {
        tasks.removeAll {
            it.id == id
        }
    }
}
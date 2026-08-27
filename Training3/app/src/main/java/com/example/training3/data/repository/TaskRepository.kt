package com.example.training3.data.repository

import com.example.training3.data.task.Category
import com.example.training3.data.task.Priority
import com.example.training3.data.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTask(): Flow<List<Task>>

    suspend fun addTask(title: String, content: String, category: Category, priority: Priority)

    suspend fun toggleFavorite(id: Int)

    suspend fun updateTask(task: Task)

    suspend fun deleteTaskById(id: Int)

}
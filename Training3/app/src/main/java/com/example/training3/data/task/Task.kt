package com.example.training3.data.task


data class Task(
    val id: Int,
    val title: String,
    val content: String,
    val category: Category = Category.PERSONAL,
    val priority: Priority = Priority.MEDIUM,
    var isFavorite: Boolean = false
)

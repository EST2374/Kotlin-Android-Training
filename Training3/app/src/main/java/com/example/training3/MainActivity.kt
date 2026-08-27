package com.example.training3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.training3.data.repository.TaskRepository
import com.example.training3.data.repository.TaskRepositoryImpl
import com.example.training3.data.task.Task
import com.example.training3.model.TaskViewModel
import com.example.training3.screens.TaskScreen
import com.example.training3.ui.theme.Training3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val taskRepository = TaskRepositoryImpl()
            val taskViewModel = TaskViewModel(taskRepository = taskRepository)
            Training3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskScreen(
                        viewModel = taskViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
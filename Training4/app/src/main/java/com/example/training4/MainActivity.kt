package com.example.training4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.training4.model.HabitViewModel
import com.example.training4.navigation.HabitNavDisplay
import com.example.training4.ui.theme.Training4Theme
import org.koin.androidx.compose.koinViewModel

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val taskViewModel: HabitViewModel = koinViewModel()
            Training4Theme {
                HabitNavDisplay(viewModel = taskViewModel)
            }
        }
    }
}

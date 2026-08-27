package com.example.training2

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
import com.example.training2.model.NoteViewModel
import com.example.training2.screens.NoteScreen
import com.example.training2.ui.theme.Training2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteViewModel = NoteViewModel()
            Training2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoteScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = noteViewModel
                    )
                }
            }
        }
    }
}
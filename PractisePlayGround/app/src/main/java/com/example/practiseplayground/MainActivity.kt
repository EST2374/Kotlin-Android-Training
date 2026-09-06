package com.example.practiseplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practiseplayground.model.AnimalShelterViewModel
import com.example.practiseplayground.navigation.AnimalRootNavigation
import com.example.practiseplayground.screens.AnimalShelterScreen
import com.example.practiseplayground.ui.theme.PractisePlayGroundTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AnimalShelterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PractisePlayGroundTheme {
                AnimalRootNavigation(
                    viewModel
                )
            }
        }
    }
}
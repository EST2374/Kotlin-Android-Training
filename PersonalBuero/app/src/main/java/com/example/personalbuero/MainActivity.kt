package com.example.personalbuero

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
import com.example.personalbuero.model.PersonalBueroViewModel
import com.example.personalbuero.navigation.PersonalBueroRootNavigation
import com.example.personalbuero.ui.theme.PersonalBueroTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PersonalBueroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalBueroTheme {
                PersonalBueroRootNavigation(
                    viewModel
                )
            }
        }
    }
}
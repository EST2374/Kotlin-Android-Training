package com.example.practiseplayground.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.practiseplayground.model.AnimalDetailsViewModel
import com.example.practiseplayground.retrofit.RetrofitTodo
import com.example.practiseplayground.retrofit.Todo
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailsScreen(
    onBack: () -> Unit,
    viewModel: AnimalDetailsViewModel
    ) {

    val animal by viewModel.getAnimal.collectAsState()
    var selectedTodo by remember { mutableStateOf<Todo?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Name: ${animal?.name}")
        Text("Age: ${animal?.age}")
        Text("Species: ${animal?.species}")
        Text("Availability: ${animal?.status}")

        LaunchedEffect(
            Unit
        ) {
            selectedTodo = viewModel.getTodo()
        }

        Text("Todo: ${selectedTodo?.title}")

        IconButton(
            onClick = { onBack() }
        ) {
            Icon(
                Icons.Default.ArrowBack,
                "BackArrow"
            )
        }
    }

}
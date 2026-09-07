package com.example.personalbuero.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.personalbuero.model.PersonalBueroUiState
import com.example.personalbuero.model.PersonalBueroViewModel
import com.example.personalbuero.screens.components.BottomBarNavigation

//TODO(Make Screens and Second ViewModel)

@Composable
fun PersonalBueroListScreen(
    viewModel: PersonalBueroViewModel,
    onNavigationToList: () -> Unit,
    onNavigationToDetails: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBarNavigation(
                onNavigationToList,
                onNavigationToDetails
            )
        },
    ) { innerPadding ->

        when(val state = uiState) {
            is PersonalBueroUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "An error happened: ${state.error}")
                }
            }
            PersonalBueroUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading Data")
                }
            }
            is PersonalBueroUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn {
                        items(state.employees, key = { it.id }) { employee ->
                            Text(employee.name)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("OffDays: ")
                                LazyRow {
                                    items(employee.offDays) { day ->
                                        Text(day.name)
                                        Spacer(
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
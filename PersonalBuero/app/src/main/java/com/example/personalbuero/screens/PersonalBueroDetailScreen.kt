package com.example.personalbuero.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.personalbuero.model.PersonalBueroViewModel
import com.example.personalbuero.screens.components.BottomBarNavigation

@Composable
fun PersonalBueroDetailsScreen(
    viewModel: PersonalBueroViewModel,
    onNavigationToList: () -> Unit,
    onNavigationToDetails: () -> Unit
) {

    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                onNavigationToList,
                onNavigationToDetails
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Details")
        }
    }

}
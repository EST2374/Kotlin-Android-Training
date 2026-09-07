package com.example.personalbuero.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomBarNavigation(
    onNavigationToList: () -> Unit,
    onNavigationToDetails: () -> Unit
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { onNavigationToList() }) {
                    Icon(Icons.Filled.List, contentDescription = "List Screen")
                }
                IconButton(onClick = { onNavigationToDetails() }) {
                    Icon(
                        Icons.Filled.Details,
                        contentDescription = "Details Screen",
                    )
                }
            }
        },
    )
}
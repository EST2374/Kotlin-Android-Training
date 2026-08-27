package com.example.training.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.training.model.UserViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel
) {
    val userData by userViewModel.getUser.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                userViewModel.getUserData()
            }
        ) {
            Text(text = "Click for Data!")
        }
        userData?.name?.let {
            Text(text = "Name = $it")
        }
        userData?.age?.let {
            Text(text = "age = $it")
        }
    }
}
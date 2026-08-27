package com.example.training4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.training4.data.repository.HabitEvent
import com.example.training4.data.repository.HabitState

// Input Form Card
@Composable
fun AddEditHabitScreen(
    state: HabitState,
    onEvent: (HabitEvent) -> Unit,
    modifier: Modifier = Modifier

) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(HabitEvent.HideDialog)
        },
        title = { Text("Add Content") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(HabitEvent.SetHabitTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(HabitEvent.SetHabitDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
                /* TODO("Make it to a button and add Frequency")
                TextField(
                    value = state.category,
                    onValueChange = {
                        onEvent(HabitEvent.SetHabitCategory())
                    },
                    placeholder = {
                        Text(text = "Category")
                    }
                )

                 */
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Button(
                    onClick = {
                        onEvent(HabitEvent.SaveHabit)
                    }
                ) {
                    Text("Save Habit")
                }
            }
        }
    )
}
package com.example.training4.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory

@Composable
fun HabitCard(
    habit: Habit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: () -> Unit,
    onHabitClick: (Habit) -> Unit,
    modifier: Modifier = Modifier
) {
    val priorityColor = when (habit.category) {
        HabitCategory.HEALTH -> Color.Green
        HabitCategory.PRODUCTIVITY -> Color.Red
        HabitCategory.LEARNING -> Color.Yellow
        HabitCategory.MINDFULNESS -> Color.Blue
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = { onHabitClick(habit) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (habit.isCompletedToday) Color.Gray else Color.Unspecified
                    )
                    SuggestionChip(
                        onClick = { },
                        label = { Text(habit.category.name) },
                        border = BorderStroke(2.dp, priorityColor)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak badge",
                        tint = if (habit.currentStreak > 0) Color(0xFFFF9800) else Color.LightGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${habit.currentStreak}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (habit.description.isNotEmpty()) {
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 2
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
                IconButton(
                    onClick = onDoneClick,
                    enabled = !habit.isCompletedToday
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Done",
                        tint = if (habit.isCompletedToday) Color.Green else Color.Unspecified
                    )
                }
            }
        }
    }
}

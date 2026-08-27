package com.example.training3.screens.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.training3.data.task.Priority

@Composable
fun PriorityButtons(
    selectedPriority: Priority = Priority.LOW,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = Priority.entries

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, priority ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onPrioritySelected(priority) },
                selected = priority == selectedPriority,
                label = { Text(priority.name.lowercase().replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}

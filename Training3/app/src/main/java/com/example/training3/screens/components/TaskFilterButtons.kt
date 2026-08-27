package com.example.training3.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.training3.data.task.TaskFilter

@Composable
fun TaskFilterButtons(
    activeFilter: TaskFilter,
    onFilterSelected: (TaskFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = TaskFilter.entries

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        options.forEachIndexed { index, filter ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onFilterSelected(filter) },
                selected = filter == activeFilter,
                label = {
                    Text(
                        filter.name.lowercase().replaceFirstChar { it.uppercase() }
                            .replace("_", " ")
                    )
                }
            )
        }
    }
}

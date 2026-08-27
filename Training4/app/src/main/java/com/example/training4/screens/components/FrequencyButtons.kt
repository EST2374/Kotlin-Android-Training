package com.example.training4.screens.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.training4.data.habit.Frequency

@Composable
fun FrequencyButtons(
    selectedFrequency: Frequency = Frequency.DAILY,
    onFrequencySelected: (Frequency) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = Frequency.entries

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, priority ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onFrequencySelected(priority) },
                selected = priority == selectedFrequency,
                label = { Text(priority.name.lowercase().replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}
package com.example.training3.screens.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.training3.data.task.Category

@Composable
fun CategoryButtons(
    selectedCategory: Category = Category.PERSONAL,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = Category.entries

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, category ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onCategorySelected(category) },
                selected = category == selectedCategory,
                label = { Text(category.name.lowercase().replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}

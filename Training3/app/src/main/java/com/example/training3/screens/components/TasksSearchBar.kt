package com.example.training3.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchBarState = rememberSearchBarState()
    val textFieldState = TextFieldState(query)
    val scope = rememberCoroutineScope()

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text }.collect {
            onQueryChange(it.toString())
        }
    }

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {
                    Text(
                        modifier = Modifier.clearAndSetSemantics {},
                        text = "Search tasks...",
                        color = Color.Gray
                    )
                },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Search, "Search") },
            )
        }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        state = searchBarState,
        inputField = inputField,
    )
}

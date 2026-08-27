package com.example.training2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.training2.model.NoteUiState
import com.example.training2.model.NoteViewModel

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel
) {

    val titleState = remember {
        TextFieldState()
    }

    val contentState = remember {
        TextFieldState()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .padding(top=40.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(

            ) {
                TextField(
                    state = titleState,
                    placeholder = {
                        Text(text = "Enter the title")
                    }
                )
                Spacer(
                    modifier = Modifier.padding(30.dp)
                )
                TextField(
                    state = contentState,
                    placeholder = {
                        Text(text = "Content or Delete ID")
                    }
                )
            }

            Column(

            ) {
                Button(
                    onClick = {
                        viewModel.addNote(
                            titleState.text.toString(),
                            contentState.text.toString()
                        )
                        titleState.clearText()
                        contentState.clearText()
                    }
                ) {
                    Text(text = "Add Note")
                }
                Spacer(
                    modifier = Modifier.padding(30.dp)
                )
                Button(
                    onClick = {
                        viewModel.deleteNote(
                            contentState.text.toString().toInt()
                        )
                        contentState.clearText()
                    }
                ) {
                    Text(text = "Delete Note")
                }
            }

        }
        Spacer(
            modifier = Modifier.padding(30.dp)
        )
        when(val notes = viewModel.uiState.collectAsStateWithLifecycle().value) {
            is NoteUiState.Error -> {
                Text(text = notes.message)
            }
            NoteUiState.Loading -> {
                CircularProgressIndicator()
            }
            is NoteUiState.Success -> {
                LazyColumn(

                ) {
                    items(notes.notes) { note ->
                        Text(text = "ID = ${note.id}")
                        Text(text = "Title = ${note.title}")
                        Text(text = "Content = ${note.content}")
                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                }
            }
        }

    }

}
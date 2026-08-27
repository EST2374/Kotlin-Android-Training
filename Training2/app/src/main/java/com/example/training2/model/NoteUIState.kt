package com.example.training2.model

import com.example.training2.data.note.Note

sealed class NoteUiState {
    object Loading : NoteUiState()
    data class Success(val notes: List<Note>) : NoteUiState()
    data class Error(val message: String) : NoteUiState()
}
package com.example.training2.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training2.data.note.Note
import com.example.training2.data.repository.NoteRepository
import com.example.training2.data.repository.NoteRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class NoteViewModel(
    private val noteRepository: NoteRepository = NoteRepositoryImpl()
): ViewModel() {

    private val _uiState = MutableStateFlow<NoteUiState>(NoteUiState.Loading)


    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init {
        getNotes()
    }

    fun getNotes(){
        viewModelScope.launch {

            try {
                _uiState.value = NoteUiState.Loading
                delay(2000.milliseconds)

                val notes = noteRepository.getNotes()
                _uiState.value = NoteUiState.Success(notes)
            } catch (e: Exception) {
                _uiState.value = NoteUiState.Error(e.message ?: "Something went wrong")
            }

        }
    }

    fun addNote(title:String, content: String) {
        viewModelScope.launch {

            noteRepository.addNote(title,content)

            getNotes()

        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {

            noteRepository.deleteNoteById(id)

            getNotes()

        }
    }

}



package com.example.training2.data.repository

import com.example.training2.data.note.Note

interface NoteRepository {

    suspend fun getNotes(): List<Note>
    suspend fun addNote(title: String, content: String)

    suspend fun deleteNoteById(id: Int)

}

class NoteRepositoryImpl: NoteRepository {

    private val notes = mutableListOf<Note>()
    private var nextId = 0


    override suspend fun getNotes(): List<Note> {
        return notes
    }

    override suspend fun addNote(title: String, content: String) {
        val newNote = Note(
            id = nextId,
            title = title,
            content = content
        )
        notes.add(newNote)
        nextId++
    }

    override suspend fun deleteNoteById(id: Int) {
        notes.removeAt(id)
    }

}
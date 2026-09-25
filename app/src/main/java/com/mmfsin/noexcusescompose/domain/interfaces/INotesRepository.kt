package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface INotesRepository {
    suspend fun createNewNote(title: String, description: String)

    suspend fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: String): Note?
}
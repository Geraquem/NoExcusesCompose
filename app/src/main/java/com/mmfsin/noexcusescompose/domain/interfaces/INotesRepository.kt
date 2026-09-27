package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface INotesRepository {
    suspend fun saveNote(noteId: String?, title: String, text: String)

    fun getNotes(): Flow<List<Note>>
    fun getNoteById(noteId: String): Note?

    fun updatePinnedNote(noteId: String)
    fun getPinnedNote(): Flow<Note?>
}
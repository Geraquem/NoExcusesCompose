package com.mmfsin.noexcusescompose.data.repository

import com.mmfsin.noexcusescompose.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcusescompose.data.mappers.createNoteDTO
import com.mmfsin.noexcusescompose.data.mappers.toNote
import com.mmfsin.noexcusescompose.data.mappers.toNoteList
import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import com.mmfsin.noexcusescompose.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepository @Inject constructor(
    val notesDAO: NotesDAO,
) : INotesRepository {

    override suspend fun createNewNote(title: String, description: String) {
        val noteDTO = createNoteDTO(title, description)
        notesDAO.insertNote(noteDTO)
    }

    override suspend fun getNotes(): Flow<List<Note>> {
        return notesDAO.getNotes().map { it.toNoteList() }
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return notesDAO.getNoteById(noteId)?.toNote()
    }
}
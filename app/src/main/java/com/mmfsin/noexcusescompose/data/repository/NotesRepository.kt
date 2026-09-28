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

    override suspend fun saveNote(
        noteId: String?,
        title: String,
        text: String,
        pinned: Boolean
    ) {
        val noteDTO = createNoteDTO(noteId, title, text, pinned)
        notesDAO.insertNote(noteDTO)
    }

    override fun getNotes(): Flow<List<Note>> {
        return notesDAO.getNotes().map { it.toNoteList().sortedBy { n -> n.date }.reversed() }
    }

    override fun getNoteById(noteId: String): Note? {
        return notesDAO.getNoteById(noteId)?.toNote()
    }

    override fun updatePinnedNote(noteId: String) {
        notesDAO.updatePinnedNote(noteId)
    }

    override fun getPinnedNote(): Flow<Note?> {
        return notesDAO.getPinnedNote().map { it?.toNote() }
    }
}
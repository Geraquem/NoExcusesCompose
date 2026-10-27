package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcuses.data.mappers.createNoteDTO
import com.mmfsin.noexcuses.data.mappers.toNote
import com.mmfsin.noexcuses.data.mappers.toNoteList
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.models.Note
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

    override fun deleteNote(noteId: String) = notesDAO.deleteNote(noteId)
}
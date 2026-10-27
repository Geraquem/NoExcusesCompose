package com.mmfsin.noexcuses.presentation.notes

import com.mmfsin.noexcuses.domain.models.Note

data class NotesStates(
    val isLoading: Boolean = true,

    val noteToDelete: Note? = null,

    val notes: List<Note> = emptyList(),
)
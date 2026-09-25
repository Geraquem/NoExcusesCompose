package com.mmfsin.noexcusescompose.presentation.notes

import com.mmfsin.noexcusescompose.domain.models.Note

data class NotesStates(
    val isLoading: Boolean = true,

    val notes: List<Note> = emptyList(),
)
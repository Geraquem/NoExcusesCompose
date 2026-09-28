package com.mmfsin.noexcusescompose.presentation.notes.create

data class NoteDetailStates(
    val noteId: String? = null,
    val noteTitle: String = "",
    val noteText: String = "",
    val notePinned: Boolean = false,

    val shouldGoBack: Boolean = false,

    val sww: Boolean = false,
)
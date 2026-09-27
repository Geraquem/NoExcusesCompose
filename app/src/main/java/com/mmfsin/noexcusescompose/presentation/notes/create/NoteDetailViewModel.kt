package com.mmfsin.noexcusescompose.presentation.notes.create

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.noexcusescompose.domain.usecases.GetNoteByIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.SaveNoteUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
) : BaseViewModel<NoteDetailStates>(NoteDetailStates()) {

    private val noteId: String? = savedStateHandle["noteId"]

    init {
        noteId?.let { id -> getNoteById(id) }
    }

    fun getNoteById(noteId: String) {
        executeUseCase(
            { getNoteByIdUseCase(noteId) },
            { note ->
                if (note == null) sww()
                else {
                    _uiState.update {
                        it.copy(
                            noteId = note.id,
                            noteTitle = note.title,
                            noteText = note.description,
                        )
                    }
                }
            },
            { sww() }
        )
    }

    fun saveNote() {
        val states = uiState.value
        val title = states.noteTitle
        val text = states.noteText

        if (title.isNotBlank() || text.isNotBlank()) {
            executeUseCase(
                {
                    saveNoteUseCase(
                        noteId = states.noteId,
                        title = title,
                        text = text
                    )
                },
                { goBack() },
                { sww() }
            )
        } else goBack()
    }

    fun goBack() = _uiState.update { it.copy(shouldGoBack = true) }

    fun updateTitle(value: String) = _uiState.update { it.copy(noteTitle = value) }
    fun updateText(value: String) = _uiState.update { it.copy(noteText = value) }

    private fun sww() = _uiState.update { it.copy(sww = true) }
}
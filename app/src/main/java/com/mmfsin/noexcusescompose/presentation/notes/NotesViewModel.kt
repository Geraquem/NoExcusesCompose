package com.mmfsin.noexcusescompose.presentation.notes

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.usecases.DeleteNoteUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetNotesUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdatePinnedNoteUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val updatePinnedNoteUseCase: UpdatePinnedNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : BaseViewModel<NotesStates>(NotesStates()) {

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { notes ->
                _uiState.update {
                    it.copy(
                        notes = notes,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updatePushpin(noteId: String) {
        executeUseCase(
            { updatePinnedNoteUseCase(noteId) },
            { println("Pinned Note updated") },
            {},
        )
    }

    fun updateNoteToDelete(note: Note?) = _uiState.update { it.copy(noteToDelete = note) }

    fun deleteNote(noteId: String) {
        executeUseCase(
            { deleteNoteUseCase(noteId) },
            { updateNoteToDelete(null) },
            {}
        )
    }
}
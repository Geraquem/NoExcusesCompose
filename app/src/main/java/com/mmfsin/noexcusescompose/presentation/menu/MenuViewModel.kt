package com.mmfsin.noexcusescompose.presentation.menu

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetPinnedNoteUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdatePinnedNoteUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getPinnedNoteUseCase: GetPinnedNoteUseCase,
    private val updatePinnedNoteUseCase: UpdatePinnedNoteUseCase,
) : BaseViewModel<MenuStates>(MenuStates()) {

    init {
        getMuscularGroups()
        getPinnedNote()
    }

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase() },
            { muscularGroups ->
                _uiState.update {
                    it.copy(
                        muscularGroups = muscularGroups
                    )
                }
            },
            {},
        )
    }

    fun getPinnedNote() {
        viewModelScope.launch {
            getPinnedNoteUseCase().collect { note ->
                _uiState.update { it.copy(actualNote = note) }
            }
        }
    }

    fun showUnpinNoteDialog(value: Boolean) = _uiState.update { it.copy(showUnpinNoteDialog = value) }

    fun unpinNote(noteId: String) {
        executeUseCase(
            { updatePinnedNoteUseCase(noteId) },
            { _uiState.update { it.copy(showUnpinNoteDialog = false) } },
            {},
        )
    }

    fun sww() = _uiState.update { it.copy(sww = true) }
}
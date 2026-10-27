package com.mmfsin.noexcusescompose.presentation.menu

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetAllExercisesUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetPinnedNoteUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetPinnedRoutineUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdatePinnedNoteUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdatePinnedRoutineUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getPinnedRoutineUseCase: GetPinnedRoutineUseCase,
    private val getPinnedNoteUseCase: GetPinnedNoteUseCase,
    private val updatePinnedRoutineUseCase: UpdatePinnedRoutineUseCase,
    private val updatePinnedNoteUseCase: UpdatePinnedNoteUseCase,
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
) : BaseViewModel<MenuStates>(MenuStates()) {

    init {
        getMuscularGroups()
        getPinnedRoutine()
        getPinnedNote()
        getAllExercises()
    }

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase() },
            { muscularGroups ->
                _uiState.update {
                    it.copy(
                        muscularGroups = muscularGroups,
                        isLoading = false
                    )
                }
            },
            {},
        )
    }

    fun getPinnedRoutine() {
        viewModelScope.launch {
            getPinnedRoutineUseCase().collect { routine ->
                _uiState.update { it.copy(actualRoutine = routine) }
            }
        }
    }

    fun getPinnedNote() {
        viewModelScope.launch {
            getPinnedNoteUseCase().collect { note ->
                _uiState.update { it.copy(actualNote = note) }
            }
        }
    }

    fun getAllExercises() {
        executeUseCase(
            { getAllExercisesUseCase() },
            {},
            {}
        )
    }

    fun showUnpinRoutineDialog(value: Boolean) = _uiState.update { it.copy(showUnpinRoutineDialog = value) }
    fun showUnpinNoteDialog(value: Boolean) = _uiState.update { it.copy(showUnpinNoteDialog = value) }

    fun unpinRoutine(routineId: String) {
        executeUseCase(
            { updatePinnedRoutineUseCase(routineId) },
            { showUnpinRoutineDialog(false) },
            {},
        )
    }

    fun unpinNote(noteId: String) {
        executeUseCase(
            { updatePinnedNoteUseCase(noteId) },
            { showUnpinNoteDialog(false) },
            {},
        )
    }

    fun sww() = _uiState.update { it.copy(sww = true) }
}
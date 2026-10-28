package com.mmfsin.noexcuses.presentation.menu

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.usecases.GetAllExercisesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMenuImagesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.domain.usecases.GetPinnedNoteUseCase
import com.mmfsin.noexcuses.domain.usecases.GetPinnedRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdatePinnedNoteUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdatePinnedRoutineUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuImagesUseCase: GetMenuImagesUseCase,
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
    private val getPinnedRoutineUseCase: GetPinnedRoutineUseCase,
    private val getPinnedNoteUseCase: GetPinnedNoteUseCase,
    private val updatePinnedRoutineUseCase: UpdatePinnedRoutineUseCase,
    private val updatePinnedNoteUseCase: UpdatePinnedNoteUseCase,
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
) : BaseViewModel<MenuStates>(MenuStates()) {

    init {
        getMenuImages()
        getMuscularGroups()
        getPinnedRoutine()
        getPinnedNote()
        getAllExercises()
    }

    fun getMenuImages() {
        executeUseCase(
            { getMenuImagesUseCase() },
            { images ->
                images?.let { i ->
                    _uiState.update {
                        it.copy(
                            imageMyRoutines = i.myRoutinesImageUrl,
                            imageDfRoutines = i.dfRoutinesImageUrl,
                            imageExercises = i.exercisesImageUrl,
                            imageFavorites = i.favoritesImageUrl
                        )
                    }
                }
            },
            {},
        )
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
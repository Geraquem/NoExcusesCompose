package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.CreateOrEditRoutineUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetMyRoutinesUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdatePinnedRoutineUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val getMyRoutinesUseCase: GetMyRoutinesUseCase,
    private val createOrEditRoutineUseCase: CreateOrEditRoutineUseCase,
    private val updatePinnedRoutineUseCase: UpdatePinnedRoutineUseCase,
) : BaseViewModel<MyRoutinesStates>(MyRoutinesStates()) {

    init {
        getMyRoutines()
    }

    private fun getMyRoutines() {
        viewModelScope.launch {
            getMyRoutinesUseCase().collect { routines ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myRoutines = routines
                    )
                }
            }
        }
    }

    fun createOrEditRoutine(routineId: String?) {
        val states = uiState.value
        val desc = states.newRoutineDescription.ifBlank { null }
        executeUseCase(
            { createOrEditRoutineUseCase(routineId, states.newRoutineName, desc) },
            {
                _uiState.update {
                    it.copy(
                        newRoutineName = "",
                        newRoutineDescription = "",
                        showCreateRoutineDialog = false
                    )
                }
            },
            { sww() },
        )
    }

    fun updateNewRoutineName(value: String) = _uiState.update { it.copy(newRoutineName = value) }
    fun updateNewRoutineDescription(value: String) = _uiState.update { it.copy(newRoutineDescription = value) }

    fun showCreateRoutineDialog(value: Boolean) = _uiState.update { it.copy(showCreateRoutineDialog = value) }

    fun updatePushpin(routineId: String) {
        executeUseCase(
            { updatePinnedRoutineUseCase(routineId) },
            { println("Pinned routine updated") },
            {},
        )
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}
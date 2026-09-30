package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.CreateRoutineUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetMyRoutinesUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val getMyRoutinesUseCase: GetMyRoutinesUseCase,
    private val createRoutineUseCase: CreateRoutineUseCase,
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
            { createRoutineUseCase(routineId, states.newRoutineName, desc) },
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

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}
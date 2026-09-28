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
    private val createRoutineUseCase: CreateRoutineUseCase
) : BaseViewModel<MyRoutinesStates>(MyRoutinesStates()) {

    init {
        getMyRoutines()
    }

    private fun getMyRoutines() {
        viewModelScope.launch {
            getMyRoutinesUseCase().collect { routines ->
                _uiState.update { it.copy(myRoutines = routines) }
            }
        }
    }

    fun createRoutine(name: String, description: String) {
        executeUseCase(
            { createRoutineUseCase(name, description) },
            { _uiState.update { it.copy(showCreateRoutineDialog = false) } },
            { sww() },
        )
    }

    fun showCreateRoutineDialog(value: Boolean) = _uiState.update { it.copy(showCreateRoutineDialog = value) }

    fun sww() = _uiState.update { it.copy(sww = true) }
}
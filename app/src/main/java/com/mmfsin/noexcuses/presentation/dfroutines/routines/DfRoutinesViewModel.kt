package com.mmfsin.noexcuses.presentation.dfroutines.routines

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.domain.usecases.AddDfRoutineToMyRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDfRoutineDaysUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDfRoutinesUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DfRoutinesViewModel @Inject constructor(
    private val getDfRoutinesUseCase: GetDfRoutinesUseCase,
    private val getDfRoutineDaysUseCase: GetDfRoutineDaysUseCase,
    private val addDfRoutineToMyRoutineUseCase: AddDfRoutineToMyRoutineUseCase,
) : BaseViewModel<DfRoutinesStates>(DfRoutinesStates()) {

    init {
        getDfRoutines()
    }

    fun getDfRoutines() {
        viewModelScope.launch {
            getDfRoutinesUseCase().collect { routines ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        routines = routines
                    )
                }
            }
        }
    }

    fun getDays(routineId: String) {
        executeUseCase(
            { getDfRoutineDaysUseCase(routineId) },
            {},
            {}
        )
    }

    fun updateRoutineToAdd(value: Routine?) = _uiState.update { it.copy(routineToAdd = value) }
    fun updateRoutineAdded(value: Boolean) = _uiState.update { it.copy(routineAdded = value) }

    fun addRoutine() {
        val states = uiState.value
        states.routineToAdd?.let { routine ->
            executeUseCase(
                { addDfRoutineToMyRoutineUseCase(routine.id) },
                {
                    updateRoutineToAdd(null)
                    viewModelScope.launch {
                        updateRoutineAdded(true)
                        delay(4000)
                        updateRoutineAdded(false)
                    }
                },
                { sww() }
            )
        }
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}
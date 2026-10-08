package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.domain.usecases.CreateRoutineUseCase
import com.mmfsin.noexcusescompose.domain.usecases.DeleteDayUseCase
import com.mmfsin.noexcusescompose.domain.usecases.DeleteRoutineUseCase
import com.mmfsin.noexcusescompose.domain.usecases.EditRoutineUseCase
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
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
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

    fun createRoutine(name: String, description: String?) {
        executeUseCase(
            { createRoutineUseCase(name, description) },
            { _uiState.update { it.copy(showCreateRoutineDialog = false) } },
            { sww() },
        )
    }

    fun editRoutine(name: String, description: String?) {
        val states = uiState.value
        states.routineToEdit?.let { routine ->
            val editedRoutine = routine.copy(
                name = name,
                description = description
            )
            executeUseCase(
                { editRoutineUseCase(editedRoutine) },
                { updateRoutineToEdit(null) },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun deleteRoutine() {
        val states = uiState.value
        states.routineToEdit?.let { routine ->
            executeUseCase(
                { deleteRoutineUseCase(routine.id) },
                {
                    _uiState.update {
                        it.copy(
                            routineToEdit = null,
                            showDeleteRoutineDialog = false,
                        )
                    }
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun deleteDay() {
        val states = uiState.value
        states.dayToDelete?.let { day ->
            executeUseCase(
                { deleteDayUseCase(day.id) },
                { _uiState.update { it.copy(dayToDelete = null) } },
                { sww() }
            )
        }
    }

    fun updateRoutineToEdit(value: Routine?) = _uiState.update { it.copy(routineToEdit = value) }
    fun updateDayToDelete(value: Day?) = _uiState.update { it.copy(dayToDelete = value) }

    fun showDeleteRoutineDialog(value: Boolean) = _uiState.update { it.copy(showDeleteRoutineDialog = value) }
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
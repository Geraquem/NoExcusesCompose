package com.mmfsin.noexcusescompose.presentation.myroutines.days

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.usecases.CreateOrEditDayUseCase
import com.mmfsin.noexcusescompose.domain.usecases.DeleteExerciseRtnUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetExercisesRtnByDayIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetRoutineByIdUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val createOrEditDayUseCase: CreateOrEditDayUseCase,
    private val getExercisesRtnByDayIdUseCase: GetExercisesRtnByDayIdUseCase,
    private val deleteExerciseRtnUseCase: DeleteExerciseRtnUseCase,
) : BaseViewModel<DayStates>(DayStates()) {

    val routineId: String? = savedStateHandle["routineId"]
    val dayId: String? = savedStateHandle["dayId"]

    init {
        setRoutineId(routineId)
        getDay(dayId)
    }

    fun setRoutineId(routineId: String?) {
        if (routineId == null) sww()
        else _uiState.update { it.copy(routineId = routineId) }
    }

    fun getDay(dayId: String?) {
        if (dayId == null) {
            routineId?.let { id -> getMyRoutine(id, null) }
            _uiState.update {
                it.copy(
                    dayId = UUID.randomUUID().toString(),
                    isLoading = false
                )
            }
        } else {
            executeUseCase(
                { getDayByIdUseCase(dayId) },
                { day ->
                    day?.let {
                        _uiState.update {
                            it.copy(
                                routineId = routineId,
                                dayId = dayId,
                                dayName = day.name,
                            )
                        }
                        getMyRoutine(routineId = day.routineId, dayId = day.id)
                        getDayExercises(dayId = day.id)
                    } ?: run { sww() }
                },
                { sww() },
            )
        }
    }

    fun getMyRoutine(routineId: String, dayId: String?) {
        executeUseCase(
            { getRoutineByIdUseCase(routineId) },
            { routine ->
                routine?.let {
                    val position = if (dayId == null) routine.days.size
                    else routine.days.indexOfFirst { it.id == dayId }

                    _uiState.update {
                        it.copy(
                            routineName = routine.name,
                            dayOrder = position + 1
                        )
                    }
                }
            },
            {},
        )
    }

    fun getDayExercises(dayId: String) {
        viewModelScope.launch {
            getExercisesRtnByDayIdUseCase(dayId).collect { exercises ->
                _uiState.update {
                    it.copy(
                        exercises = exercises,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateDayName(value: String) = _uiState.update {
        it.copy(
            dayName = value,
            emptyNameError = false
        )
    }

    fun createOrEditDay() {
        val states = uiState.value
        if (states.routineId == null) sww()
        else {
            if (states.dayName.isNotBlank()) {
                executeUseCase(
                    { createOrEditDayUseCase(states.routineId, states.dayId, states.dayName) },
                    { shouldGoBack() },
                    { sww() }
                )
            } else _uiState.update { it.copy(emptyNameError = true) }
        }
    }

    fun handleBack() {
        val states = uiState.value
        if (states.dayName.isNotBlank()) createOrEditDay()
        else shouldGoBack()
    }

    fun deleteExerciseRtn() {
        val states = uiState.value
        states.exerciseRtnToEdit?.id?.let { exerciseRtnId ->
            executeUseCase(
                { deleteExerciseRtnUseCase(exerciseRtnId) },
                {
                    _uiState.update {
                        it.copy(
                            showDeleteExerciseRtnDialog = false,
                            exerciseRtnToEdit = null
                        )
                    }
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun showEditExerciseRtn(value: ExerciseRtn?) = _uiState.update { it.copy(exerciseRtnToEdit = value) }

    fun showDeleteExerciseRtnDialog(value: Boolean) = _uiState.update { it.copy(showDeleteExerciseRtnDialog = value) }

    fun shouldGoBack() = _uiState.update { it.copy(shouldGoBack = true) }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}
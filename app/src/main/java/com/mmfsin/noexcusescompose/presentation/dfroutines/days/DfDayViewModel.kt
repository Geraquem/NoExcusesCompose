package com.mmfsin.noexcusescompose.presentation.dfroutines.days

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.noexcusescompose.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDfDayExercisesUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetRoutineByIdUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DfDayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val getDayExercisesUseCase: GetDfDayExercisesUseCase,
) : BaseViewModel<DfDayStates>(DfDayStates()) {

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
        if (dayId == null) sww()
        else {
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
        val states = uiState.value
        states.routineId?.let { routineId ->
            executeUseCase(
                { getDayExercisesUseCase(routineId, dayId) },
                { exercises ->
                    _uiState.update {
                        it.copy(
                            exercises = exercises,
                            isLoading = false
                        )
                    }
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}
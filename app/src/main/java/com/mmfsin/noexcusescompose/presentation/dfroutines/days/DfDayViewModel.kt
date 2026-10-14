package com.mmfsin.noexcusescompose.presentation.dfroutines.days

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDfRoutineDaysUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDfRoutinesUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetRoutineByIdUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import com.mmfsin.noexcusescompose.presentation.dfroutines.routines.DfRoutinesStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DfDayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
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
//        viewModelScope.launch {
//            getExercisesRtnByDayIdUseCase(dayId).collect { exercises ->
//                _uiState.update {
//                    it.copy(
//                        exercises = exercises,
//                        isLoading = false
//                    )
//                }
//            }
//        }
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}